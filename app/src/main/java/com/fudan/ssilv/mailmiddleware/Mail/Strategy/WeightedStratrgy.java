package com.fudan.ssilv.mailmiddleware.Mail.Strategy;
import java.util.Random;
/**
 * Created by ssilv on 2017/5/19.
 */

public class WeightedStratrgy implements MailReceiveStrategy {
    public int decide(int index)
    {
        Random random = new Random();
        int x = random.nextInt(10);
        if (x<6)
            return 0;
        else
            return 1;
    }
}
