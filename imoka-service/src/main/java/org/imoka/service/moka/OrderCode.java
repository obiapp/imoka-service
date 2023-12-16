/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service.moka;

import org.imoka.core.moka7.S7OrderCode;

/**
 *
 * @author r.hendrick
 */
public class OrderCode extends S7OrderCode {

    /**
     * Convenient method to directly get firmware string version
     *
     * @return firmware version as V1.V2.V3
     */
    public static String firmware(S7OrderCode orderCode) {
        return orderCode.V1 + "." + orderCode.V2 + "." + orderCode.V3;
    }
}
