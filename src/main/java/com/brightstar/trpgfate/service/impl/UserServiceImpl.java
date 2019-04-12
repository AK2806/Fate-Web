package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.component.staticly.datetime.DatetimeConverter;
import com.brightstar.trpgfate.component.staticly.uuid.UUIDHelper;
import com.brightstar.trpgfate.config.custom_property.AvatarConfig;
import com.brightstar.trpgfate.config.security.SaltedSha256PasswordEncoder;
import com.brightstar.trpgfate.dao.AccountDAO;
import com.brightstar.trpgfate.dao.AnnouncementDAO;
import com.brightstar.trpgfate.dao.NotificationDAO;
import com.brightstar.trpgfate.dao.UserDAO;
import com.brightstar.trpgfate.dao.po.Notification;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.AccountInfo;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.UserConflictException;
import com.brightstar.trpgfate.service.exception.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final class UserImpl implements User {
        private int id;
        private String email;
        private int role;
        private Calendar createTime;
        private boolean active;

        @Override
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        @Override
        public Calendar getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Calendar createTime) {
            this.createTime = createTime;
        }

        @Override
        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private SaltedSha256PasswordEncoder passwordEncoder;
    @Autowired
    private AvatarConfig avatarConfig;
    @Autowired
    private NotificationDAO notificationDAO;
    @Autowired
    private AnnouncementDAO announcementDAO;

    @Override
    public User getUser(int id) throws UserDoesntExistException {
        User ret = generateUserFromPO(userDAO.getById(id));
        if (ret == null) throw new UserDoesntExistException();
        return ret;
    }

    @Override
    public User getUser(String email) throws UserDoesntExistException {
        User ret = generateUserFromPO(userDAO.findByEmail(email));
        if (ret == null) throw new UserDoesntExistException();
        return ret;
    }

    private User generateUserFromPO(com.brightstar.trpgfate.dao.po.User po) {
        if (po == null) return null;
        UserImpl ret = new UserImpl();
        ret.setId(po.getId());
        ret.setEmail(po.getEmail());
        ret.setRole(po.getRole());
        ret.setCreateTime(DatetimeConverter.sqlTimestamp2Calendar(po.getCreateTime()));
        ret.setActive(po.isActive());
        return ret;
    }

    @Override
    public void registerWithEmail(String email, String password, int role) throws UserConflictException {
        if (userDAO.findByEmail(email) != null) throw new UserConflictException();
        com.brightstar.trpgfate.dao.po.User po = new com.brightstar.trpgfate.dao.po.User();
        po.setEmail(email);
        po.setPasswdSha256Salted(Hex.decode(passwordEncoder.encode(password)));
        po.setRole(role);
        po.setCreateTime(DatetimeConverter.calendar2SqlTimestamp(Calendar.getInstance()));
        po.setActive(true);
        userDAO.insert(po);
        com.brightstar.trpgfate.dao.po.Account accountPo = new com.brightstar.trpgfate.dao.po.Account();
        accountPo.setUserId(po.getId());
        accountPo.setName(email);
        accountPo.setAvatarId(UUIDHelper.toBytes(UUID.fromString(avatarConfig.getDefaultUUID())));
        accountPo.setGender(AccountInfo.GENDER_UNKNOWN);
        accountPo.setPrivacy(AccountInfo.PRIVACY_PRIVATE);
        accountDAO.insert(accountPo);
        Notification notificationPo = new Notification();
        notificationPo.setUserId(po.getId());
        notificationPo.setLastTimeRead(DatetimeConverter.calendar2SqlTimestamp(Calendar.getInstance()));
        notificationDAO.insert(notificationPo);
    }

    @Override
    public void modifyPassword(User user, String newPassword) {
        com.brightstar.trpgfate.dao.po.User po = userDAO.getById(user.getId());
        po.setPasswdSha256Salted(Hex.decode(passwordEncoder.encode(newPassword)));
        userDAO.updatePassword(po);
    }

    @Override
    public AccountInfo getAccountInfo(User user) {
        com.brightstar.trpgfate.dao.po.Account po = accountDAO.findById(user.getId());
        AccountInfo ret = new AccountInfo();
        ret.setName(po.getName());
        ret.setAvatar(UUIDHelper.fromBytes(po.getAvatarId()));
        ret.setGender(po.getGender());
        Date birthday = po.getBirthday();
        if (birthday != null) ret.setBirthday(DatetimeConverter.sqlDate2Calendar(birthday));
        else ret.setBirthday(null);
        ret.setResidence(po.getResidence());
        ret.setPrivacy(po.getPrivacy());
        return ret;
    }

    @Override
    public void updateAccountInfo(User user, AccountInfo accountInfo) {
        com.brightstar.trpgfate.dao.po.Account po = new com.brightstar.trpgfate.dao.po.Account();
        po.setUserId(user.getId());
        po.setName(accountInfo.getName());
        po.setAvatarId(UUIDHelper.toBytes(accountInfo.getAvatar()));
        po.setGender(accountInfo.getGender());
        Calendar birthday = accountInfo.getBirthday();
        if (birthday != null) po.setBirthday(DatetimeConverter.calendar2SqlDate(birthday));
        else po.setBirthday(null);
        po.setResidence(accountInfo.getResidence());
        po.setPrivacy(accountInfo.getPrivacy());
        accountDAO.update(po);
    }
}
