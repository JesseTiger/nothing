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
package vip.xiaonuo.modular.book.entity;

import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import vip.xiaonuo.core.pojo.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 黄金屋
 *
 * @author jesse
 * @date 2024-01-18 19:29:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("l_book")
@ExcelTarget("Book")
@AllArgsConstructor
@NoArgsConstructor
public class Book extends BaseEntity {

    /**
     * id
     */
    @Excel(name = "ID", width = 10,orderNum = "1")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 封面
     */
    @Excel(name = "封面", width = 10,orderNum = "2")
    private String cover;

    /**
     * 名称
     */
    @Excel(name = "名称", width = 20,orderNum = "3")
    private String title;

    /**
     * 副标题
     */
    @Excel(name = "副标题", width = 20,orderNum = "4")
    private String subtitle;

    /**
     * 原作名
     */
    @Excel(name = "原作名", width = 10,orderNum = "5")
    private String originalTitle;

    /**
     * 作者
     */
    @Excel(name = "作者", width = 20,orderNum = "6")
    private String author;

    /**
     * 出版社
     */
    @Excel(name = "出版社", width = 20,orderNum = "7")
    private String publishingHouse;

    /**
     * 出品方
     */
    @Excel(name = "出品方", width = 10,orderNum = "8")
    private String producer;

    /**
     * 译者
     */
    @Excel(name = "译者", width = 10,orderNum = "9")
    private String translator;

    /**
     * 出版年
     */
    @Excel(name = "出版年", width = 10,orderNum = "10")
    private String publicationYear;

    /**
     * 页数
     */
    @Excel(name = "页数", width = 10,orderNum = "11")
    private Integer page;

    /**
     * 定价
     */
    @Excel(name = "定价", width = 10,orderNum = "12")
    private double pricing;

    /**
     * 装帧
     */
    @Excel(name = "装帧", width = 10,orderNum = "13")
    private String binding;

    /**
     * ISBN
     */
    @Excel(name = "ISBN", width = 20,orderNum = "14")
    private String isbn;

    /**
     * 简介
     */
    @Excel(name = "简介", width = 50,orderNum = "15")
    private String content;

    /**
     * 有效位.0:有效,1:无效
     */
    private Integer isDeleted;

}
