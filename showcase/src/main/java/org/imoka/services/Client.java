/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.services;


import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.imoka.core.moka7.*;
import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <h1>Client</h1>
 *
 * <h2>Description</h2>
 *
 *
 * @author r.hendrick
 */
@ManagedBean(name = "client")
@SessionScoped
public class Client implements Serializable {

    /**
     * Long for elapse time
     */
    private Long elapsed;

    /**
     * ipAddress PLC ip address like "10.1.21.14"
     */
    private String ipAddress;
    /**
     * Rack Default is 0 for S7300
     */
    private Integer rack;
    /**
     * Slot Default is 2 for S7300
     */
    private Integer slot;

    /**
     * Connect check if a connection exist
     */
    private Boolean connect = false;

    /**
     * S7Client
     */
    private S7Client s7Client = new S7Client();

    /**
     * Output
     */
    private String out = "";

    
    private List<BlockType> blockTypes = new ArrayList<>();

    
    private Integer blockType = 0;
    private Integer blockNumber = 0;

    /**
     * Counter execution time
     */
    private void elapseInit() {
        elapsed = System.currentTimeMillis();
    }

    /**
     * End of counter execution time
     *
     * @return elapse time in ms
     */
    private Long elapseEnd() {
        return System.currentTimeMillis() - elapsed;
    }

    /**
     * This handle a clean for the out
     */
    public void handleOutputClean() {
        out = "";
    }

    /**
     * Handle a connection request. Initialize the client (s7Client). Define a
     * connection Type, before trying to connect to specify ipAddress, rack and
     * slot. <br >
     * When finished it set the result of connection to connect. <br >
     * All message are print out with utility
     *
     *
     */
    public void handleConnect() {
/*
        elapseInit();

        /*
        out += JsfUtil.cssH3("HandleConnect");
        out += JsfUtil.htmlTxtStart();
        out += JsfUtil.cssBold("Client - Connect To : ") + JsfUtil.htmlTxt("[IP : " + ipAddress + "] [RACK : " + rack + "] [SLOT : " + slot + "]");
        */
/*        
        if (s7Client != null) {
            s7Client.Disconnect();
            /*out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Disconnect : ") + JsfUtil.htmlSucced("S7 Client Disconnected");*/
/*        }else{
            /*out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Disconnect : ") + JsfUtil.htmlSucced("S7 Client not exist");*/
/*        }

        ///
        s7Client = new S7Client();
        s7Client.SetConnectionType(S7.OP);
        /*out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Setup Connection Type [" + S7.OP + "] : ") + JsfUtil.htmlSucced("execute setup connection type [" + S7.OP + " successfuly");
            */
        ///
/*        int Result = s7Client.ConnectTo(this.ipAddress, this.rack, this.slot);
        /*out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Execute connectTo : ");*/
/*        if (Result == 0) {
            /*out += JsfUtil.htmlSucced("done successfuly >> IP(" + this.ipAddress
                    + ") Rack(" + this.rack
                    + ") Slot(" + this.slot
                    + ")");
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("PDU negotiated : ") + JsfUtil.htmlTxt(s7Client.PDULength() + " bytes");*/
/*        } else {
            /*out += JsfUtil.htmlError("error while connectTo  IP(" + this.ipAddress
                    + ") Rack(" + this.rack
                    + ") Slot(" + this.slot
                    + ") !!! " + S7Client.ErrorText(Result)) + JsfUtil.htmlTxtEnd();*/
/*            connect = false;
        }
        connect = Result == 0;
        /*out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Execution time ") + JsfUtil.htmlTxt(elapseEnd() + " ms ");
        out += JsfUtil.htmlTxtEnd();*/
    }

    /**
     * This method read PLC INFORMATION Like code, Order
     */
    public void handleSysInfo() {
        elapseInit();
        int Result;
        /*out += JsfUtil.cssH3("GetOrderCode") + JsfUtil.htmlTxtStart();*/

        /// Check if connection was establish
        if (!connect) {
            /*out += JsfUtil.htmlError("Connecton was no set !")
                    + JsfUtil.htmlTxtEnd();*/
            return;
        }

        /// 
        /*out += JsfUtil.cssBold("Init. S7 Order Code : create");*/
        S7OrderCode orderCode = new S7OrderCode();
        Result = s7Client.GetOrderCode(orderCode);
        /*out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Execute S7 Client GetOrderCode : ");*/
        if (Result == 0) {
            /*out += JsfUtil.htmlSucced("done successfuly >> Order Code(" + orderCode.Code() + ")");
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Firmware version : ");
            out += JsfUtil.htmlTxt(orderCode.V1 + "." + orderCode.V2 + "." + orderCode.V3);*/
        } else {
            /*out += JsfUtil.htmlError("error while GetOrderCode  Code(" + orderCode.Code()
                    + ") !!!" + S7Client.ErrorText(Result));*/
        }
        /*out += JsfUtil.cssBold("Execution time ")
                + JsfUtil.htmlTxt(elapseEnd() + " ms");*/

        ///
        elapseInit();
        /*out += JsfUtil.cssH3("GetCpuInfo");*/
        S7CpuInfo CpuInfo = new S7CpuInfo();
        Result = s7Client.GetCpuInfo(CpuInfo);
        /*out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Execute S7 Client GetCpuInfo : ");*/
        if (Result == 0) {
            /*out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Module Type Name : ") + JsfUtil.htmlTxt(CpuInfo.ModuleTypeName());
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Serial Number    : ") + JsfUtil.htmlTxt(CpuInfo.SerialNumber());
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("AS Name          : ") + JsfUtil.htmlTxt(CpuInfo.ASName());
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("CopyRight        : ") + JsfUtil.htmlTxt(CpuInfo.Copyright());
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Module Name      : ") + JsfUtil.htmlTxt(CpuInfo.ModuleName());*/
        } else {
            /*out += JsfUtil.htmlError("error while GetCpuInfo : " + S7Client.ErrorText(Result));*/
        }
        /*out += JsfUtil.cssBold("Execution time ") + JsfUtil.htmlTxt(elapseEnd() + " ms");*/

        elapseInit();
        /*out += JsfUtil.cssH3("GetCpInfo");*/
        S7CpInfo CpInfo = new S7CpInfo();
        Result = s7Client.GetCpInfo(CpInfo);
        /*out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Execute S7 Client GetCpInfo : ");*/
        if (Result == 0) {
            /*out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Max PDU Length    : ") + JsfUtil.htmlTxt(String.valueOf(CpInfo.MaxPduLength));
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Max connections   : ") + JsfUtil.htmlTxt(String.valueOf(CpInfo.MaxConnections));
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Max MPI rate (bps): ") + JsfUtil.htmlTxt(String.valueOf(CpInfo.MaxMpiRate));
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Max Bus rate (bps): ") + JsfUtil.htmlTxt(String.valueOf(CpInfo.MaxBusRate));
            */
        } else {
           /* out += JsfUtil.htmlError("error while GetCpInfo  " + S7Client.ErrorText(Result));*/
            //return;
        }
        /*out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Execution time ")
                + JsfUtil.htmlTxt(elapseEnd() + " ms") + JsfUtil.htmlTxtEnd();*/
    }

    /**
     * Read PLC Date and TIme
     *
     */
    public void handleDateAndTime() {
        elapseInit();
        //out += JsfUtil.cssH3("GetPlCDateTime");
        Date PlcDateTime = new Date();
        int Result = s7Client.GetPlcDateTime(PlcDateTime);
        //out += JsfUtil.htmlTxtStart() + JsfUtil.cssBold("Execute S7 Client GetPlCDateTime : ");
        if (Result == 0) {
            //out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("CPU Date/Time    : ") + JsfUtil.htmlTxt(PlcDateTime.toString());
        } else {
            //out += JsfUtil.htmlError("error while GetPlCDateTime  " + S7Client.ErrorText(Result));
        }
        /*out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Execution time ")
                + JsfUtil.htmlTxt(elapseEnd() + " ms") + JsfUtil.htmlTxtEnd();*/
    }

    /**
     *
     * @param BlockType
     * @param BlockNumber
     */
    public void handleBlockInfo() {
        elapseInit();
        //out += JsfUtil.cssH3("GetAgBlockInfo");
        S7BlockInfo Block = new S7BlockInfo();
        //out += JsfUtil.htmlTxtStart() + JsfUtil.cssBold("Execute S7 Client GetAgBlockInfo : ");
        int Result = s7Client.GetAgBlockInfo(blockType, blockNumber, Block);
        if (Result == 0) {
            /*
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Block Flags     : ") + JsfUtil.htmlTxt(Integer.toBinaryString(Block.BlkFlags()));
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Block Number    : ") + JsfUtil.htmlTxt(String.valueOf(Block.BlkNumber()));
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Block Languege  : ") + JsfUtil.htmlTxt(String.valueOf(Block.BlkLang()));
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Load Size       : ") + JsfUtil.htmlTxt(String.valueOf(Block.LoadSize()));
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("SBB Length      : ") + JsfUtil.htmlTxt(String.valueOf(Block.SBBLength()));
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Local Data      : ") + JsfUtil.htmlTxt(String.valueOf(Block.LocalData()));
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("MC7 Size        : ") + JsfUtil.htmlTxt(String.valueOf(Block.MC7Size()));
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Author          : ") + JsfUtil.htmlTxt(String.valueOf(Block.Author()));
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Family          : ") + JsfUtil.htmlTxt(String.valueOf(Block.Family()));
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Header          : ") + JsfUtil.htmlTxt(String.valueOf(Block.Header()));
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Version         : ") + JsfUtil.htmlTxt(String.valueOf(Block.Version()));
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Checksum        : 0x") + JsfUtil.htmlTxt(Integer.toHexString(Block.Checksum()));
            */
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
            /*out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Code Date       : ") + JsfUtil.htmlTxt(ft.format(Block.CodeDate()));
            out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Interface Date  : ") + JsfUtil.htmlTxt(ft.format(Block.IntfDate()));
            */
        } else {
            //out += JsfUtil.htmlError(JsfUtil.cssBold("error while GetAgBlockInfo  ") + S7Client.ErrorText(Result));
        }
        /*out += JsfUtil.htmlTxtBreak() + JsfUtil.cssBold("Execution time ")
                + JsfUtil.htmlTxt(elapseEnd() + " ms") + JsfUtil.htmlTxtEnd();*/
    }

    

    
    /**
     * 
     * @return 
     */
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getRack() {
        return rack;
    }

    public void setRack(Integer rack) {
        this.rack = rack;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public String getOutput() {
        return out;
    }

    public void setOutput(String out) {
        this.out = out;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public List<BlockType> getBlockTypes() {
        if (blockTypes.isEmpty()) {
            blockTypes.add(new BlockType("BLOCK DB", S7.Block_DB));
            blockTypes.add(new BlockType("BLOCK FB", S7.Block_FB));
            blockTypes.add(new BlockType("BLOCK FC", S7.Block_FC));
            blockTypes.add(new BlockType("BLOCK OB", S7.Block_OB));
            blockTypes.add(new BlockType("BLOCK SDB", S7.Block_SDB));
            blockTypes.add(new BlockType("BLOCK SFB", S7.Block_SFB));
            blockTypes.add(new BlockType("BLOCK SFC", S7.Block_SFC));
        }
        return blockTypes;
    }

    public Integer getBlockType() {
        return blockType;
    }

    public void setBlockType(Integer blockType) {
        this.blockType = blockType;
    }
    
    



    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

}
