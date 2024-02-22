/*
Copyright [2020] [https://www.xiaonuo.vip]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Snowy源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/xiaonuobase/snowy-layui
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/xiaonuobase/snowy-layui
6.若您的项目无法满足以上几点，可申请商业授权，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.modular.book.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.core.exception.ServiceException;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.pojo.page.PageResult;
import vip.xiaonuo.core.util.PoiUtil;
import vip.xiaonuo.modular.book.entity.Book;
import vip.xiaonuo.modular.book.enums.BookExceptionEnum;
import vip.xiaonuo.modular.book.mapper.BookMapper;
import vip.xiaonuo.modular.book.param.BookParam;
import vip.xiaonuo.modular.book.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 黄金屋service接口实现类
 *
 * @author jesse
 * @date 2024-01-18 19:29:10
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private BookMapper bookMapper;
    @Override
    public PageResult<Book> page(BookParam bookParam) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(bookParam)) {

            // 根据名称 查询
            if (ObjectUtil.isNotEmpty(bookParam.getTitle())) {
                queryWrapper.lambda().like(Book::getTitle, bookParam.getTitle());
            }
            // 根据作者 查询
            if (ObjectUtil.isNotEmpty(bookParam.getAuthor())) {
                queryWrapper.lambda().like(Book::getAuthor, bookParam.getAuthor());
            }
            // 根据ISBN 查询
            if (ObjectUtil.isNotEmpty(bookParam.getIsbn())) {
                queryWrapper.lambda().eq(Book::getIsbn, bookParam.getIsbn());
            }
            queryWrapper.orderByDesc("create_time").orderByDesc("update_time");
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<Book> list(BookParam bookParam) {
        return this.list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(BookParam bookParam) {
        Book book = new Book();
        BeanUtil.copyProperties(bookParam, book);
        if(checkBookExist(bookParam)){
            throw new ServiceException(BookExceptionEnum.NOT_EXIST.getCode(),
                "书名:<<"+book.getTitle() +">>作者:{"+book.getAuthor()
                +"},出版社:{"+book.getPublishingHouse()+"}"
                +BookExceptionEnum.ALREADY_EXISTED.getMessage());
        }
        this.save(book);
        // redisTemplate.opsForValue().append("bl:"+book.getTitle(), JSON.toJSONString(book));
        // redisTemplate.opsForValue().get("bl:" +book.getTitle());
    }

    private Boolean checkBookExist(BookParam bookParam) {

        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(bookParam)) {
            String author = bookParam
                .getAuthor();
            String title = bookParam.getTitle();
            String publishingHouse = bookParam.getPublishingHouse();
            // 根据名称 查询
            if (ObjectUtil.isNotEmpty(title)) {
                queryWrapper.lambda().eq(Book::getTitle, bookParam.getTitle());
            }
            // 根据作者 查询
            if (ObjectUtil.isNotEmpty(author)) {
                queryWrapper.lambda().eq(Book::getAuthor, bookParam.getAuthor());
            }
            // 根据出版社 查询
            if (ObjectUtil.isNotEmpty(publishingHouse)) {
                queryWrapper.lambda().eq(Book::getPublishingHouse, bookParam.getPublishingHouse());
            }
            List<Book> list = baseMapper.selectList(queryWrapper);
            if(!CollectionUtils.isEmpty(list)){
                return true;
            }
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BookParam> bookParamList) {
        bookParamList.forEach(bookParam -> {
        Book book = this.queryBook(bookParam);
            this.removeById(book.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BookParam bookParam) {
        Book book = this.queryBook(bookParam);
        BeanUtil.copyProperties(bookParam, book);
        this.updateById(book);
    }

    @Override
    public Book detail(BookParam bookParam) {
        return this.queryBook(bookParam);
    }

    @Override
    public Integer bookImport(MultipartFile file) {
        List<Book> importList = PoiUtil.importExcel(file, 0, 1, Book.class);
        assert importList != null;
        return  bookImportSave(importList);
        // for (Book book : importList) {
        //     BookParam bookParam =new BookParam();
        //     BeanUtil.copyProperties(book,bookParam);
        //     add(bookParam);
        //
        // }
        // System.err.println(JSON.toJSONString(importList));
        // return importList.size();
    }

    private Integer bookImportSave(List<Book> importList) {
        AtomicReference<Integer> savedCount= new AtomicReference<>(0);
        importList.forEach(i->{
            BookParam bookParam =new BookParam();
            BeanUtil.copyProperties(i,bookParam);
            if(importSave(bookParam)){
                savedCount.getAndSet(savedCount.get() + 1);
            }
        });
        return savedCount.get();
    }

    private Boolean importSave(BookParam bookParam) {
        Book book = new Book();
        BeanUtil.copyProperties(bookParam, book);
        if(checkBookExist(bookParam)){
            return false;
        }
        return this.save(book);
    }
    /**
     * 获取黄金屋
     *
     * @author jesse
     * @date 2024-01-18 19:29:10
     */
    private Book queryBook(BookParam bookParam) {
        Book book = this.getById(bookParam.getId());
        if (ObjectUtil.isNull(book)) {
            throw new ServiceException(BookExceptionEnum.NOT_EXIST);
        }
        return book;
    }
}
