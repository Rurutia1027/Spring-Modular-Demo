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

package org.medium.demo.project.service;

import lombok.RequiredArgsConstructor;
import org.medium.demo.project.entity.ShortLinkDO;
import org.medium.demo.project.repository.ShortLinkRepository;
import org.medium.demo.project.repository.ShortLinkRouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShortLinkService {
    private final ShortLinkRepository shortLinkRepository;
    private final ShortLinkRouteRepository shortLinkRouteRepository;

    public ShortLinkDO createShortLink(ShortLinkDO shortLinkDO) {
        return shortLinkRepository.save(shortLinkDO);
    }

    public Optional<ShortLinkDO> getByShortUri(String shortUri) {
        return shortLinkRepository.findByShortUri(shortUri);
    }

    public List<ShortLinkDO> getAll() {
        return shortLinkRepository.findAll();
    }

    @Transactional
    public void deleteByShortUri(String shortUri) {
        shortLinkRepository.deleteByShortUri(shortUri);
    }

    @Transactional
    public ShortLinkDO incrementClickNum(String shortUri) {
        ShortLinkDO link = shortLinkRepository.findByShortUri(shortUri)
                .orElseThrow(() -> new RuntimeException("ShortLink not found"));
        link.setClickNum(link.getClickNum() + 1);
        return shortLinkRepository.save(link);
    }

    /**
     * Query short link via querying route table first
     */
    public Optional<ShortLinkDO> getByRouteGid(String gid) {
        return shortLinkRouteRepository.findByGid(gid)
                .flatMap(route -> shortLinkRepository.findByShortUri(route.getFullShortUrl()));
    }

    public Optional<ShortLinkDO> getByRouteFullUrl(String fullShortUrl) {
        return shortLinkRouteRepository.findByFullShortUrl(fullShortUrl)
                .flatMap(route -> shortLinkRepository.findByShortUri(route.getFullShortUrl()));
    }
}
