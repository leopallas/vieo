package com.demo.db;

public enum Indicator {
    LOGIN_STATUS_ONLINE(1, "在线"), LOGIN_STATUS_OFFLINE(2, "离线"),

    PICTURE_TYPE_NORMAL(1, "普通"), PICTURE_TYPE_WARN(2, "告警"), PICTURE_TYPE_EXCEPTION(3, "异常"), PICTURE_TYPE_FAULT(4,
            "故障"),

    //修程
    XC_CX(1, "厂修"), XC_DX(2, "段修"), XC_LX(3, "临修"), XC_SGLX(4, "事故临修"), XC_SGDX(5,"事故段修"),

    //部位
    BW_CG(1, "车钩"), BW_CT(2, "车体"), BW_CJ(3, "底架"), BW_FZ(4,"翻转"), BW_GH(5,"钩缓"),
    BW_JJPJ(6,"集经配件"),BW_JJYQ(7,"集经油漆"),BW_LH(8,"连缓"),BW_LC(9,"轮轴"),
    BW_NZD(10,"内制动"),BW_RDPJ(11,"入段配件"),BW_YQ(12,"油漆"),BW_ZD(13,"制动"),
    BW_ZDL(14,"制动梁"),BW_ZDZZ(15,"制动装置"),BW_ZXJ(16,"转向架"),

    //故障分级
    GZFJ_A(1, "A类故障"), GZFJ_B(2, "B类故障"), GZFJ_C(3, "C类故障"),

    //故障分类
    GZFL_ZCLC(1, "整车落成"), GZFL_ZJCC(2, "中间抽查"), GZFL_RDPJ(3, "入段配件"), GZFL_PJLC(4, "配件落成")

    ;


    private int    code;
    private String label;

    Indicator(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}
