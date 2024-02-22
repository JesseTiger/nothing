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
package vip.xiaonuo.modular.book.controller;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.core.annotion.BusinessLog;
import vip.xiaonuo.core.annotion.Permission;
import vip.xiaonuo.core.enums.LogAnnotionOpTypeEnum;
import vip.xiaonuo.core.pojo.page.PageResult;
import vip.xiaonuo.core.pojo.response.ResponseData;
import vip.xiaonuo.core.pojo.response.SuccessResponseData;
import vip.xiaonuo.modular.book.entity.Book;
import vip.xiaonuo.modular.book.param.BookParam;
import vip.xiaonuo.modular.book.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.List;
import vip.xiaonuo.sys.modular.user.param.SysUserParam;

/**
 * 黄金屋控制器
 *
 * @author jesse
 * @date 2024-01-18 19:29:10
 */
@Controller
public class BookController {

    private String PATH_PREFIX = "book/";

    @Resource
    private BookService bookService;

    /**
     * 黄金屋页面
     *
     * @author jesse
     * @date 2024-01-18 19:29:10
     */
    @Permission
    @GetMapping("/book/index")
    public String index() {
        return PATH_PREFIX + "index.html";
    }

    /**
     * 黄金屋表单页面
     *
     * @author jesse
     * @date 2024-01-18 19:29:10
     */
    @GetMapping("/book/form")
    public String form() {
        return PATH_PREFIX + "form.html";
    }

    /**
     * 查询黄金屋
     *
     * @author jesse
     * @date 2024-01-18 19:29:10
     */
    @Permission
    @ResponseBody
    @GetMapping("/book/page")
    @BusinessLog(title = "黄金屋_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public PageResult<Book> page(BookParam bookParam) {
        return bookService.page(bookParam);
    }

    /**
     * 添加黄金屋
     *
     * @author jesse
     * @date 2024-01-18 19:29:10
     */
    @Permission
    @ResponseBody
    @PostMapping("/book/add")
    @BusinessLog(title = "黄金屋_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(BookParam.add.class) BookParam bookParam) {
        bookService.add(bookParam);
        return new SuccessResponseData();
    }

    /**
     * 删除黄金屋
     *
     * @author jesse
     * @date 2024-01-18 19:29:10
     */
    @Permission
    @ResponseBody
    @PostMapping("/book/delete")
    @BusinessLog(title = "黄金屋_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(BookParam.delete.class) List<BookParam> bookParamList) {
        bookService.delete(bookParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑黄金屋
     *
     * @author jesse
     * @date 2024-01-18 19:29:10
     */
    @Permission
    @ResponseBody
    @PostMapping("/book/edit")
    @BusinessLog(title = "黄金屋_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(BookParam.edit.class) BookParam bookParam) {
        bookService.edit(bookParam);
        return new SuccessResponseData();
    }

    /**
     * 查看黄金屋
     *
     * @author jesse
     * @date 2024-01-18 19:29:10
     */
    @Permission
    @ResponseBody
    @GetMapping("/book/detail")
    @BusinessLog(title = "黄金屋_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(BookParam.detail.class) BookParam bookParam) {
        return new SuccessResponseData(bookService.detail(bookParam));
    }

    /**
     * 黄金屋列表
     *
     * @author jesse
     * @date 2024-01-18 19:29:10
     */
    @Permission
    @ResponseBody
    @GetMapping("/book/list")
    @BusinessLog(title = "黄金屋_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(BookParam bookParam) {
        return new SuccessResponseData(bookService.list(bookParam));
    }


    /**
     * 导入
     */
    @Permission
    @ResponseBody
    @PostMapping("/book/import")
    @BusinessLog(title = "book_导入", opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData bookImport(@RequestPart("file") MultipartFile file) {
        System.err.println(file.getName()+"======"+file.getSize());
        return new SuccessResponseData(bookService.bookImport(file));
    }
}
