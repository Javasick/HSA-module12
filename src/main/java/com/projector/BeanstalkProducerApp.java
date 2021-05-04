/*
 * *************************************************************************
 * * Yaypay CONFIDENTIAL   2021
 * * All Rights Reserved. * *
 * NOTICE: All information contained herein is, and remains the property of Yaypay Incorporated and its suppliers, if any.
 * The intellectual and technical concepts contained  herein are proprietary to Yaypay Incorporated
 * and its suppliers and may be covered by U.S. and Foreign Patents, patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material  is strictly forbidden unless prior written permission is obtained  from Yaypay Incorporated.
 */

package com.projector;

import com.dinstone.beanstalkc.BeanstalkClientFactory;
import com.dinstone.beanstalkc.Configuration;
import com.dinstone.beanstalkc.JobProducer;

import java.util.logging.Logger;

import static com.projector.Constants.BATCH_SIZE;
import static com.projector.Constants.BEANSTALKD_TUBE;
import static com.projector.Constants.MSG;

/*
 * Author : Pavlo Omelianchuk
 * Date Created: 2021/05/04
 */
public class BeanstalkProducerApp {
    private static final Logger log = Logger.getLogger(new Object() { }.getClass().getEnclosingClass().getName());

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setServiceHost("127.0.0.1");
        config.setServicePort(11300);
        BeanstalkClientFactory factory = new BeanstalkClientFactory(config);
        JobProducer producer = factory.createJobProducer(BEANSTALKD_TUBE);

        long current = System.currentTimeMillis();
        for (int i = 0; i < BATCH_SIZE; i++) {
            producer.putJob(100, 0, 5, MSG.getBytes());
        }
        log.info("" + BATCH_SIZE + " messages produced during " + (System.currentTimeMillis() - current) + " ms");
        producer.close();
    }
}
