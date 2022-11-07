package com.restaurant.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.Objects;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record VwPhoto(
        Long id,
        String name,
        String type,
        String photoUrl
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VwPhoto vwPhoto = (VwPhoto) o;

        if (!Objects.equals(id, vwPhoto.id)) return false;
        if (!Objects.equals(name, vwPhoto.name)) return false;
        if (!Objects.equals(type, vwPhoto.type)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
//        if (!Arrays.equals(file, vwPhoto.file)) return false;
        return Objects.equals(photoUrl, vwPhoto.photoUrl);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
//        result = 31 * result + Arrays.hashCode(file);
        result = 31 * result + (photoUrl != null ? photoUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VwPhoto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
//                ", file=" + Arrays.toString(file) +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
