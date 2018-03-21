package smile.service.home;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Package: com.example.poker
 * @Description: 房间管理实现类
 * 可以指定创建房间的规则
 * @author: liuxin
 * @date: 2018/3/10 下午8:33
 */
public class HomeManagerImpl implements HomeManager {
    /**
     * 房间信息
     */
    Map<Integer, Home> homeMap=new ConcurrentHashMap();
    /**
     * 房间生成器,可以生成多人
     */
    HomeGenerator homeGenerator;

    public HomeManagerImpl(HomeGenerator homeGenerator) {
        this.homeGenerator = homeGenerator;
    }

    /**
     * 校验房间号是否是唯一
     *
     * @return
     */
    boolean checkUniqueHome(Home home) {
        return homeMap.get(home.getHid()) != null;
    }

    /**
     * 创建房间并校验房间号,是否已经存在当不存在,就返回创建的房间
     * 当存在就一直创建
     *
     * @return
     */
    @Override
    public Home createHome(HomeInfo homeType) {
        Home home = null;
        do {
            home = homeGenerator.createHome(homeType);
        } while (checkUniqueHome(home));
        /**
         * 常见成功即添加到管理页面
         */
        addHome(home);
        return home;
    }

    /**
     * 创建一个房间
     *
     * @param home
     * @return
     */
    private boolean addHome(Home home) {
        if (homeMap == null) {
            homeMap = new ConcurrentHashMap();
        }
        Home currentHomer = homeMap.put(home.getHid(), home);
        return currentHomer != null;
    }

    @Override
    public Long homeNumber() {
        return Long.parseLong(Integer.toString(homeMap.size()));
    }
}