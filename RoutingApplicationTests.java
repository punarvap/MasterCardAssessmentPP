package com.mastercard.routing;

import com.mastercard.routing.controller.RoutingController;
import com.mastercard.routing.helpers.RouteUtils;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


import java.util.ArrayList;

import static org.mockito.Mockito.when;

@SpringBootTest
class RoutingApplicationTests {

    @Autowired
    RoutingController controller;

    @Mock
    RouteUtils utils;

    @Test
    public void testPositiveSenario() {

        ArrayList<String> list = new ArrayList<String>();

        list.add("Boston,NewYork");
        list.add("Phiadelphia,Newark");
        list.add("Newark,Boston");
        list.add("Trenton,Albany");


        when(utils.readFile(Mockito.any())).thenReturn(list);

        String check = controller.isConnected("Boston", "Phiadelphia");

        if (check == "YES") {
            assert true;
        } else {
            assert false;
        }


    }

	@Test
	public void testNegativeSenario() {

		ArrayList<String> list = new ArrayList<String>();

		list.add("Boston,NewYork");
		list.add("Phiadelphia,Newark");
		list.add("Newark,Boston");
		list.add("Trenton,Albany");


		when(utils.readFile(Mockito.any())).thenReturn(list);

		String check = controller.isConnected("Boston", "ABC");

		if (check == "NO") {
			assert true;
		} else {
			assert false;
		}


	}

}
