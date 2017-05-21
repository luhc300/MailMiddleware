package com.fudan.ssilv.mailmiddleware.Mail.Strategy;

import com.fudan.ssilv.mailmiddleware.MainActivity;

/**
 * Created by ssilv on 2017/4/26.
 */

public class AverangeStrategy implements MailReceiveStrategy {
    public int decide(int index)
    {
        return index % MainActivity.fileReceiveServiceList.size();
    }
}
