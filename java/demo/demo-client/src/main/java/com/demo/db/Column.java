package com.demo.db;

import java.util.LinkedHashMap;
import java.util.Map;

public enum Column {
    LOGIN_STATUS(Indicator.LOGIN_STATUS_ONLINE, Indicator.LOGIN_STATUS_OFFLINE),

    //修程
    XC_TYPE(Indicator.XC_CX, Indicator.XC_DX, Indicator.XC_LX, Indicator.XC_SGLX, Indicator.XC_SGDX),

    //部位
    BW_TYPE(Indicator.BW_CG, Indicator.BW_CT, Indicator.BW_CJ, Indicator.BW_FZ, Indicator.BW_GH, Indicator.BW_JJPJ,
            Indicator.BW_JJYQ, Indicator.BW_LH, Indicator.BW_LC, Indicator.BW_NZD, Indicator.BW_RDPJ, Indicator.BW_YQ,
            Indicator.BW_ZD, Indicator.BW_ZDL, Indicator.BW_ZDZZ, Indicator.BW_ZXJ),

    //故障分级
    GZFJ_TYPE(Indicator.GZFJ_A, Indicator.GZFJ_B, Indicator.GZFJ_C),

    //故障分类
    GZFL_TYPE(Indicator.GZFL_ZCLC, Indicator.GZFL_ZJCC, Indicator.GZFL_RDPJ, Indicator.GZFL_PJLC)
    ;

    private Map<Integer, String> maps = new LinkedHashMap<Integer, String>();

    Column(Indicator... vals) {
        for (Indicator it : vals) {
            maps.put(it.getCode(), it.getLabel());
        }
    }

    public static void main(String args[]) {
        // System.out.println(Column.TSK_TYPE.getLabelByCode(Indicator.TSK_TYPE_NORMAL.getCode()));
        //
        // System.out.println(Indicator.TSK_LEVE_GROUP.getCode());
        // System.out.println(Indicator.TSK_LEVE_GROUP.getLabel());
        //
        // System.out.println(Column.TSK_STATUS.getCodesAndLabels());
    }

    public String getLabelByCode(int code) {
        return maps.get(code);
    }

    public Map<Integer, String> getCodesAndLabels() {
        return maps;
    }
}
