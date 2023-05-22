package com.example.mustore.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constant {

    public static final Map<String,Object> queryMap;

    static {
//        pageSize=10&pageNumber=1&property=name&sort=id+&search=&filter={"username':"trima","id"="1"}
        queryMap=new HashMap<>();
        queryMap.put("pageSize",15  );
        queryMap.put("pageNumber",1);
        queryMap.put("sort","view+");
        queryMap.put("search","");
        queryMap.put("property","name");
//        queryMap.put("filter",10);
    }
    public static final String baseUrl="http://192.168.89.239:8085";

    public static final String socketQr="http://192.168.89.239:9090/qr";
    public static final String socketChat="http://192.168.89.239:9090/chat";

    public class CodeCategory{
        public final static String HAT="HAT";
        public final static String FOOTBALLSHIRT="FOOTBALLSHIRT";
        public final static String BALL="BALL";
        public final static String POSTER="POSTER";
        public final static String SHOE="SHOE";
    }

    public class SortCondition{
        public final static String PRICE_DESC="Giá cao đến thấp";
        public final static String PRICE_ASC="Giá thấp đến cao";
    }

}
