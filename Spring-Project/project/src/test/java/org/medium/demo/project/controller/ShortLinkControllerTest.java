/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.medium.demo.project.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.medium.demo.project.common.convention.result.Result;
import org.medium.demo.project.entity.ShortLinkDO;
import org.medium.demo.project.service.ShortLinkService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ShortLinkController.class)
class ShortLinkControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShortLinkService shortLinkService;

    @Test
    public void testMockInitOK() {
        Assertions.assertNotNull(mockMvc);
        Assertions.assertNotNull(shortLinkService);
    }

    @Test
    @SneakyThrows
    public void givenValidRequest_whenHello_thenReturnSuccess() {
        // --- given ---
        String name = UUID.randomUUID().toString();

        // --- when && then ---
        mockMvc.perform(get("/api/medium/demo/project/hello/" + name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(Result.SUCCESS_CODE))
                .andExpect(jsonPath("$.data").value("Hello " + name));
    }

    @Test
    @SneakyThrows
    public void whenGetAll_thenReturnList() {
        ShortLinkDO link = ShortLinkDO.builder().build();
        when(shortLinkService.getAll()).thenReturn(Collections.singletonList(link));

        mockMvc.perform(get("/api/medium/demo/project/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(Result.SUCCESS_CODE))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @SneakyThrows
    public void whenGetByShortUriFound_thenReturnSuccess() {
        ShortLinkDO link = new ShortLinkDO();
        Mockito.when(shortLinkService.getByShortUri(anyString())).thenReturn(Optional.of(link));

        mockMvc.perform(get("/api/medium/demo/project/short-uri/testUri"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(Result.SUCCESS_CODE))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @SneakyThrows
    public void whenGetByShortUriNotFound_thenReturnFailure() {
        Mockito.when(shortLinkService.getByShortUri(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/medium/demo/project/short-uri/notfound"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    @SneakyThrows
    public void whenCreateShortLink_thenReturnCreatedLink() {
        ShortLinkDO link = new ShortLinkDO();
        Mockito.when(shortLinkService.createShortLink(any())).thenReturn(link);

        mockMvc.perform(post("/api/medium/demo/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(Result.SUCCESS_CODE))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @SneakyThrows
    public void whenDeleteShortLink_thenReturnSuccess() {
        mockMvc.perform(delete("/api/medium/demo/project/delete/testUri"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(Result.SUCCESS_CODE));
    }

    @Test
    @SneakyThrows
    public void whenIncrementClick_thenReturnUpdatedLink() {
        ShortLinkDO link = new ShortLinkDO();
        Mockito.when(shortLinkService.incrementClickNum(anyString())).thenReturn(link);

        mockMvc.perform(post("/api/medium/demo/project/increment-click/testUri"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(Result.SUCCESS_CODE))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @SneakyThrows
    public void whenGetByRouteGid_thenReturnLink() {
        ShortLinkDO link = new ShortLinkDO();
        Mockito.when(shortLinkService.getByRouteGid(anyString())).thenReturn(Optional.of(link));

        mockMvc.perform(get("/api/medium/demo/project/route/gid/testGid"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(Result.SUCCESS_CODE))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @SneakyThrows
    public void whenGetByRouteFullUrl_thenReturnLink() {
        ShortLinkDO link = new ShortLinkDO();
        when(shortLinkService.getByRouteFullUrl(anyString())).thenReturn(Optional.of(link));

        mockMvc.perform(get("/api/medium/demo/project/route/full-url")
                        .param("fullShortUrl", "http://short.url/abc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(Result.SUCCESS_CODE))
                .andExpect(jsonPath("$.data").exists());
    }

}