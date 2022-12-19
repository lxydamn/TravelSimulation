package com.backend.service.backmanage.path;

import com.backend.pojo.Path;

import java.util.List;
import java.util.Map;

public interface GetPathsService {
    Map<String, List<Path>> getPaths();
}
