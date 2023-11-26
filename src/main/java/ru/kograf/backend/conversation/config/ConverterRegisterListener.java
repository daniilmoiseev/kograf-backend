package ru.kograf.backend.conversation.config;

import java.util.Set;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Component;
import ru.kograf.backend.conversation.service.IKografConversionService;

@Component
public class ConverterRegisterListener {

    private final Set<Converter<?, ?>> converters;
    private final IKografConversionService conversionService;

    public ConverterRegisterListener(
            Set<Converter<?, ?>> converters,
            IKografConversionService conversionService) {
        this.converters = converters;
        this.conversionService = conversionService;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        GenericConversionService service = (GenericConversionService) conversionService;
        converters.forEach(service::addConverter);
    }
}
