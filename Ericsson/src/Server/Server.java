package Server;


import java.util.List;

/**
 * Created by Harold_LIU on 3/20/16.
 */
public class Server {

    static private List<User> users;
    static private int validLoginTime = 0;
    static private int invalidLoginTime = 0;

    private void userInit()
    {
        users.add(new User("liu","123",false,0,0,0));
        users.add(new User("zhao","123",false,0,0,0));
        users.add(new User("qian","123",false,0,0,0));
        users.add(new User("sun","123",false,0,0,0));
        users.add(new User("li", "123", false, 0, 0, 0));
    }

    public  Server ()
    {
        userInit();
    }

    private boolean checkTime(User user)
    {
        long current = System.currentTimeMillis(); // 当前时间

        if (user.loginRequsetTime ==0)
        {
            user.loginDate = current;
            user.loginRequsetTime ++;
            return  true;
        }
        else if (current-user.loginDate < 1000)
        {
            if(user.loginRequsetTime<=5)
            {
                user.loginRequsetTime++;
                return  true;
            }
            else
                return false;
        }
        else
        {
            user.loginRequsetTime = 0;
            return  true;
        }

    }

    public int login (String userName, String password)
    {
        for (int i = 0; i<users.size();i++)
        {
            if (userName.equals(users.get(i).UserName))
            {
                if (checkTime(users.get(i))) {
                    if (password.equals(users.get(i).Password)) {
                        validLoginTime++;
                        users.get(i).isLogin = true;
                        return 200;
                    } else {
                        invalidLoginTime++;
                        return 201;

                    }
                }
            }
            else
            {
                invalidLoginTime++;
                return 203;
            }

        }

        invalidLoginTime ++;
        return 202;
    }
}
