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

import lombok.RequiredArgsConstructor;
import org.medium.demo.project.common.convention.result.Result;
import org.medium.demo.project.common.convention.result.Results;
import org.medium.demo.project.entity.ShortLinkDO;
import org.medium.demo.project.service.ShortLinkService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ShortLinkController
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medium/demo/project")
public class ShortLinkController {
    private final ShortLinkService shortLinkService;

    @GetMapping("/hello/{name}")
    public Result<String> hello(@PathVariable("name") String name) {
        return Results.success("Hello " + name);
    }


    // fetch all
    @GetMapping("/all")
    public Result<List<ShortLinkDO>> getAll() {
        return Results.success(shortLinkService.getAll());
    }

    // query short link via given short uri value
    @GetMapping("/short-uri/{shortUri}")
    public Result<ShortLinkDO> getByShortUri(@PathVariable String shortUri) {
        return shortLinkService.getByShortUri(shortUri)
                .map(Results::success)
                .orElseGet(() -> Results.failureFor("NOT_FOUND",
                        "ShortLink not found via route fullShortUrl"));
    }

    // fetch short link via given gid
    @GetMapping("/route/gid/{gid}")
    public Result<ShortLinkDO> getByRouteGid(@PathVariable String gid) {
        return shortLinkService.getByRouteGid(gid)
                .map(Results::success)
                .orElseGet(() -> Results.failureFor("NOT_FOUND",
                        "ShortLink not found via route fullShortUrl"));
    }

    // query route full short url query short link
    @GetMapping("/route/full-url")
    public Result<ShortLinkDO> getByRouteFullUrl(@RequestParam String fullShortUrl) {
        return shortLinkService.getByRouteFullUrl(fullShortUrl)
                .map(Results::success)
                .orElseGet(() -> Results.failureFor("NOT_FOUND",
                        "ShortLink not found via route fullShortUrl"));
    }

    // create short link
    @PostMapping("/create")
    public Result<ShortLinkDO> create(@RequestBody ShortLinkDO shortLinkDO) {
        return Results.success(shortLinkService.createShortLink(shortLinkDO));
    }

    // delete short link
    @DeleteMapping("/delete/{shortUri}")
    public Result<Void> delete(@PathVariable String shortUri) {
        shortLinkService.deleteByShortUri(shortUri);
        return Results.success();
    }

    // increment value of short link
    @PostMapping("/increment-click/{shortUri}")
    public Result<ShortLinkDO> incrementClick(@PathVariable String shortUri) {
        return Results.success(shortLinkService.incrementClickNum(shortUri));
    }
}