package com.piatnitsa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

/**
 * This class contains Spring configuration for DAO module.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
@Configuration
@EntityScan(basePackages = "com.piatnitsa")
public class DaoConfig {

}
