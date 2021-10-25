package com.hicorp.segment.security.verify;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author: wqs
 * @Date: Created in 16:27 2021/5/5
 * @Description: Marker interface for SecurityMetadataSource implementations that are designed to perform lookups keyed on FilterInvocations.
 * @ChineseDescription: SecurityMetadataSource 实现的标记接口，被设计用来执行在过滤器上键入的查找。
 * @Modified By: wqs, 19:21 2021/05/09
 */
@Service
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {

//    @Resource
//    private PermissionMapper permissionMapper;
//
//    private final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;
//
//    private static HashMap<String, Collection<ConfigAttribute>> map = null;

//    @Autowired(required = false)
//    public FilterInvocationSecurityMetadataSourceImpl(Map<RequestMatcher, Collection<ConfigAttribute>> requestMap) {
//        this.requestMap = requestMap;
//    }

    /**
     * @Author: 91074
     * @Date: Created in 19:22 2021/5/9
     * @Params: []
     * @Return: void
     * @Description: load Resource define.
     * @ChineseDescription: 用于加载权限表所有权限。
     */
//    public void loadResourceDefine() {
//
//        map = new HashMap<>();
//        Collection<ConfigAttribute> array;
//        List<Permission> permissions = permissionMapper.selectAll();
//
//        for (Permission permission :
//                permissions) {
//            array = new ArrayList<>();
//            // 添加权限名，可以添加更多权限信息，如请求方法到 ConfigAttribute 的集合中去。
//            array.add(new SecurityConfig(permission.getSummary()));
//            map.put(permission.getMethod() + permission.getApiPath(), array);
//        }
//
//    }

    /**
     * @Author: 91074
     * @Date: Created in 19:29 2021/5/9
     * @Params: [o] [java.lang.Object]
     * @Return: java.util.Collection<org.springframework.security.access.ConfigAttribute>
     * @Description: Accesses the ConfigAttributes that apply to a given secure object.
     * @ChineseDescription: 访问应用于给定安全对象的 ConfigAttributes 。
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

//        if (map == null) {
//            loadResourceDefine();
//        }
//
//        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
//        String requestCompare = request.getMethod() + request.getRequestURI();
//
//        for (String s : map.keySet()) {
//
//            if (requestCompare.equals(s)) {
//                return map.get(s);
//            }
//        }
//
//        return null;

        Collection<ConfigAttribute> co = new ArrayList<>();
        co.add(new SecurityConfig("null"));
        return co;
    }

    /**
     * @Author: 91074
     * @Date: Created in 19:30 2021/5/9
     * @Params: [] []
     * @Return: java.util.Collection<org.springframework.security.access.ConfigAttribute>
     * @Description: If available, returns all of the ConfigAttributes defined by the implementing class.
     * return null will cause a warning: Could not validate configuration attributes as the SecurityMetadataSource did not return any attributes from getAllConfigAttributes().
     * @ChineseDescription: 若可用，则返还实现类所定义的所有 ConfigAttributes 。
     * 返还 null 时，将会产生一个警告，SecurityMetadataSource未从本方法中返回任何属性。
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        Set<ConfigAttribute> allAttributes = new HashSet();
//        this.requestMap.values().forEach(allAttributes::addAll);
//        return allAttributes;
        return null;
    }

    /**
     * @Author: 91074
     * @Date: Created in 19:30 2021/5/9
     * @Params: [aClass] [java.lang.Class<?>]
     * @Return: boolean
     * @Description: Indicates whether the SecurityMetadataSource implementation is able to provide ConfigAttributes for the indicated secure object type.
     * @ChineseDescription: 指示SecurityMetadataSource实现是否能够为指定的安全对象类型提供ConfigAttributes。
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
//        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
