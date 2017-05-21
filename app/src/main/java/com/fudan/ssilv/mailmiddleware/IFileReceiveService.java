package com.fudan.ssilv.mailmiddleware;

import com.fudan.ssilv.mailmiddleware.Mail.MailBoxes.MailBoxConfig;

import java.util.ArrayList;

import javax.mail.Message;

/**
 * Created by ssilv on 2017/4/25.
 */

public interface IFileReceiveService {
    public void setMailBoxConfig(MailBoxConfig mailBoxConfig);
    public void setAttachmentPath(String attachmentPath);
    public ArrayList<Message> getMailList();
    public void receiveFileList();
    public void receiveFile(final String fileName);

}
