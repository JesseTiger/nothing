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
package vip.xiaonuo.sys.modular.msg.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.xiaonuo.sys.modular.msg.entity.SysMessageUser;
import vip.xiaonuo.sys.modular.msg.enums.SysMessageUserStatusEnum;
import vip.xiaonuo.sys.modular.msg.mapper.SysMessageUserMapper;
import vip.xiaonuo.sys.modular.msg.service.SysMessageUserService;

import java.util.List;

/**
 * 消息人员关联表service接口实现类
 *
 * @author xuyuxiang
 * @date 2021-01-22 09:37:58
 */
@Service
public class SysMessageUserServiceImpl extends ServiceImpl<SysMessageUserMapper, SysMessageUser> implements SysMessageUserService {

    @Override
    public void read(Long messageId, Long userId) {
        LambdaUpdateWrapper<SysMessageUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysMessageUser::getMessageId, messageId).eq(SysMessageUser::getReceiverId, userId)
                .set(SysMessageUser::getStatus, SysMessageUserStatusEnum.READ.getCode());
        this.update(updateWrapper);
    }

    @Override
    public void saveMessageUser(Long messageId, Long sender, List<Long> noticeUserList) {
        noticeUserList.forEach(receiver -> {
            SysMessageUser sysMessageUser = new SysMessageUser();
            sysMessageUser.setMessageId(messageId);
            sysMessageUser.setSenderId(sender);
            sysMessageUser.setReceiverId(receiver);
            sysMessageUser.setStatus(SysMessageUserStatusEnum.UNREAD.getCode());
            this.save(sysMessageUser);
        });
    }
}
