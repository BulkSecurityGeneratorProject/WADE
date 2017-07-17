package com.idscorporation.wade.cucumber.stepdefs;

import com.idscorporation.wade.WadeApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = WadeApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
