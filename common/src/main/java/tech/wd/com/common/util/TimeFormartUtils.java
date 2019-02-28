package tech.wd.com.common.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 *
 * @作者 然
 *
 * @描述 时间戳转换工具类
 *
 * @创建日期 2019/2/27 20:39
 *
 */
public class TimeFormartUtils {
    public static String getTime(long timeStample) {
        //1.得到当前的时间戳
        long now = System.currentTimeMillis();
        //2.算出当前与传入时间相差的天数
        // 在Java中，int类型的数进行除法运算，只能是整数，正是利用这一点，
        //在下列日期中，只要没过昨天的24点，无论是相差1s还是23小时，也可使用当前时间戳。
        int day = (int) (now / 1000 / 60 / 60 / 24 - timeStample / 1000 / 60 / 60 / 24);
        if (day > 7) {
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return sdf3.format(timeStample);
        } else {
            switch (day) {
                //如果是0,则说明是今天，显示时间
                case 0:
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    return sdf.format(timeStample);
                //如果是1，则说明是昨天，显示昨天+时间
                case 1:
                    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                    return "昨天" + sdf1.format(timeStample);
                //如果是2，则
                //显示前天 + 时间
                case 2:
                    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                    return "前天" + sdf2.format(timeStample);
                //如果是大于2，就显示星期几
                default:
                    SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
                    return getWork(timeStample) + sdf3.format(timeStample);
            }
        }
    }

    /**
     * 星期几
     *
     * @param time
     * @return
     */
    private static String getWork(long time) {
        Date date = new Date(time);
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK);
        return weekDays[w-1];
    }

}