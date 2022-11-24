package io.blog.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils() {}
    public static <T> T copyBean(Object src, Class<T> class_)
    {
        T res;
        try {
            res = class_.newInstance();
            BeanUtils.copyProperties(src, res);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
    public static <T, U> List<T> copyBeanList(List<U> list, Class<T> class_)
    {
        // List<T> res = new List<T>();
        // for(U i : list) {
        //     res.add(copyBean(i, class_));
        // }
        List<T> res = list.stream().map((i) -> {
            return copyBean(i, class_);
        }).collect(Collectors.toList());
        return res;
    }
}
