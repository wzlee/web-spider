package com.tseward.webspider.controller;

import com.tseward.webspider.processor.chinatax.ChinaTaxProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author tseward
 * @date 11, 06 2019
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ChinaTaxController {

    @Autowired
    private ChinaTaxProcessor chinaTaxProcessor;

    @GetMapping("/chinaTax")
    public void chinaTax(){
        chinaTaxProcessor.execute();
    }
}
