package ru.kograf.backend.conversation.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Service;

@Service
public class KografConversionServiceImpl extends GenericConversionService implements IKografConversionService {

    @Override
    public <T, U> List<U> convert(Collection<T> items, Class<U> clazz) {
        if (items == null) {
            return new ArrayList<>(0);
        }
        return items.stream().map(item -> convert(item, clazz)).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
