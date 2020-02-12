/**
 * Jackie.
 * Copyright (c)) 2019 - 2020 All Right Reserved
 */
package com.github.jackieonway.activiti.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jackie
 * @version $id: WorkFlowService.java v 0.1 2020-02-12 13:58 Jackie Exp $$
 */
public interface WorkFlowService {

    void readProcessImg(String processInstanceId, HttpServletResponse response) throws IOException;
}
