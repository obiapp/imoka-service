/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.core;

import org.imoka.core.moka7.S7;
import org.imoka.core.moka7.S7Client;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author r.hendrick
 */
public class CoreTestUnit {
    // If MakeAllTests = true, also DBWrite and Run/Stop tests will be performed
    private static final boolean MakeAllTests = false;
   
    private static long Elapsed;
    private static byte[] Buffer = new byte[65536]; // 64K buffer (maximum for S7400 systems)
    private static final S7Client Client = new S7Client();
    private static int ok=0;
    private static int ko=0;    
    private static String IpAddress = "";
    private static int Rack = 0; // Default 0 for S7300
    private static int Slot = 2; // Default 2 for S7300 
    private static int DBSample = 1; // Sample DB that must be present in the CPU
    private static int DataToMove; // Data size to read/write
    private static int CurrentStatus = S7.S7CpuStatusUnknown;
    
    public CoreTestUnit() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void CoreTestUnit() {
        
    }
}
