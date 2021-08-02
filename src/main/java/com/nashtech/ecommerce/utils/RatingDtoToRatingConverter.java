package com.nashtech.ecommerce.utils;

import com.nashtech.ecommerce.dto.RatingDto;
import com.nashtech.ecommerce.entity.Product;
import com.nashtech.ecommerce.entity.Rating;
import com.nashtech.ecommerce.entity.RatingId;
import org.modelmapper.AbstractConverter;

public class RatingDtoToRatingConverter extends AbstractConverter<RatingDto, Rating> {
    @Override
    protected Rating convert(RatingDto source) {
        Rating dest = new Rating();
        dest.setRatingId(new RatingId(source.getUserId(), source.getUserId()));
        dest.setPoint(source.getPoint());
        dest.setContent(source.getContent());
        return dest;
    }
}
