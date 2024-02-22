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
package vip.xiaonuo.modular.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.core.pojo.page.PageResult;
import vip.xiaonuo.modular.book.entity.Book;
import vip.xiaonuo.modular.book.param.BookParam;
import java.util.List;

/**
 * 黄金屋service接口
 *
 * @author jesse
 * @date 2024-01-18 19:29:10
 */
public interface BookService extends IService<Book> {

    /**
     * 查询黄金屋
     *
     * @author jesse
     * @date 2024-01-18 19:29:10
     */
    PageResult<Book> page(BookParam bookParam);

    /**
     * 黄金屋列表
     *
     * @author jesse
     * @date 2024-01-18 19:29:10
     */
    List<Book> list(BookParam bookParam);

    /**
     * 添加黄金屋
     *
     * @author jesse
     * @date 2024-01-18 19:29:10
     */
    void add(BookParam bookParam);

    /**
     * 删除黄金屋
     *
     * @author jesse
     * @date 2024-01-18 19:29:10
     */
    void delete(List<BookParam> bookParamList);

    /**
     * 编辑黄金屋
     *
     * @author jesse
     * @date 2024-01-18 19:29:10
     */
    void edit(BookParam bookParam);

    /**
     * 查看黄金屋
     *
     * @author jesse
     * @date 2024-01-18 19:29:10
     */
     Book detail(BookParam bookParam);

     Integer bookImport(MultipartFile file);
}
