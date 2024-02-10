package com.lsm.task;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionalTestExecutionListener implements TestExecutionListener {

    private TransactionStatus transactionStatus;

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        PlatformTransactionManager transactionManager = testContext.getApplicationContext()
                                                                   .getBean(PlatformTransactionManager.class);
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        PlatformTransactionManager transactionManager = testContext.getApplicationContext()
                                                                   .getBean(PlatformTransactionManager.class);
        if (transactionStatus != null) {
            transactionManager.rollback(transactionStatus);
        }
    }
}
