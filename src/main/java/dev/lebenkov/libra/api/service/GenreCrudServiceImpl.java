package dev.lebenkov.libra.api.service;

import dev.lebenkov.libra.api.util.exception.ObjectNotFoundException;
import dev.lebenkov.libra.storage.dto.GenreRequest;
import dev.lebenkov.libra.storage.dto.GenreResponse;
import dev.lebenkov.libra.storage.model.Genre;
import dev.lebenkov.libra.storage.repository.GenreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GenreCrudServiceImpl implements GenreCrudService {

    ModelMapper modelMapper;
    GenreRepository genreRepository;

    private Genre convertGenreRequestToGenre(GenreRequest genreRequest) {
        return modelMapper.map(genreRequest, Genre.class);
    }

    @Override
    public void createGenre(GenreRequest genreRequest) {
        genreRepository.save(convertGenreRequestToGenre(genreRequest));
    }

    private GenreResponse convertGenreToGenreResponse(Genre genre) {
        return modelMapper.map(genre, GenreResponse.class);
    }

    private Genre findGenreEntityByGenreId(long genreId) {
        return genreRepository.findByGenreId(genreId)
                .orElseThrow(() -> new ObjectNotFoundException("Genre with " + genreId + " not found!"));
    }

    @Override
    public GenreResponse fetchGenreById(Long genreId) {
        return convertGenreToGenreResponse(findGenreEntityByGenreId(genreId));
    }

    @Override
    public List<GenreResponse> fetchAllGenres() {
        return null;
    }

    @Override
    public void updateGenre(Long genreId, GenreRequest genreRequest) {

    }

    @Override
    public void deleteGenreById(Long genreId) {

    }
}