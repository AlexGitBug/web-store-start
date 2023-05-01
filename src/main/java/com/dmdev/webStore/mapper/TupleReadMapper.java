package com.dmdev.webStore.mapper;

import com.dmdev.webStore.dto.TupleReadDto;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TupleReadMapper implements Mapper<Tuple, TupleReadDto> {
    @Override
    public TupleReadDto map(Tuple object) {
        return new TupleReadDto(
                object.get(0, Integer.class),
                object.get(1, Integer.class)
        );
    }
}
