package ru.kograf.backend.conversation.service;

import java.util.Collection;
import java.util.List;
import org.springframework.core.convert.ConversionService;

public interface IKografConversionService extends ConversionService {

    <T, U> List<U> convert(Collection<T> items, Class<U> clazz);
}
