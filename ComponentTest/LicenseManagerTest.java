package com.HaroldLIU;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LicenseManagerTest {
	LicenseManager licenseManager;
	@Before
	public void setUp() throws Exception {
	licenseManager = new LicenseManager();
	}


	@Test
	public void testThroughputCheck() {
		//case 1
		licenseManager.ThroughputInit(8,1000,0);
		for (int i = 0;i<10;i++)
        {
            try {
                Thread.sleep(200);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            assertEquals(licenseManager.ThroughputCheck(),true);
        }
		
		//case2
		licenseManager.ThroughputInit(20,1000,1);
		for (int i = 0;i<12;i++)
        {
            try {
                Thread.sleep(200);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            assertEquals(licenseManager.ThroughputCheck(),true);
        }
		
		//case3
		licenseManager.ThroughputInit(10,1000,5);
		for (int i = 0;i<12;i++)
        {
            try {
                Thread.sleep(200);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            assertEquals(licenseManager.ThroughputCheck(),true);
        }
		
		//case5
		licenseManager.ThroughputInit(15,800,3);
		for (int i = 0;i<12;i++)
        {
            try {
                Thread.sleep(100);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            assertEquals(licenseManager.ThroughputCheck(),true);
        }
		
		//case6
		licenseManager.ThroughputInit(40,1000,20);
		for (int i = 0;i<20;i++)
        {
            try {
                Thread.sleep(200);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            assertEquals(licenseManager.ThroughputCheck(),true);
        }
		 
	}

	@Test
	public void testCapacityCheck() {
		//case 1
		licenseManager.CapacityInit(10, 0);
		 for (int i = 0; i<10;i++)
	        {
			 assertEquals(licenseManager.CapacityCheck(),true);
	        }
		 
		 //case 2
		 licenseManager.CapacityInit(20, 0);
		 for (int i = 0; i<10;i++)
	        {
			 assertEquals(licenseManager.CapacityCheck(),true);
	        }
		 
		 //case 3
		 licenseManager.CapacityInit(20, 3);
		 for (int i = 0; i<10;i++)
	        {
			 assertEquals(licenseManager.CapacityCheck(),true);
	        }
		 
		 //case 5
		 licenseManager.CapacityInit(10, 5);
		 for (int i = 0; i<5;i++)
	        {
			 assertEquals(licenseManager.CapacityCheck(),true);
	        }

		 
	}

}
