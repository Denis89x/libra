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
    GenreRetrievalService genreRetrievalService;

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

    @Override
    public GenreResponse fetchGenreById(Long genreId) {
        return convertGenreToGenreResponse(genreRetrievalService.fetchGenreEntityByGenreId(genreId));
    }

    @Override
    public List<GenreResponse> fetchAllGenres() {
        return genreRepository.findAll().stream()
                .map(this::convertGenreToGenreResponse)
                .toList();
    }

    private Genre updateGenreFromGenreRequest(Genre genre, GenreRequest genreRequest) {
        genre.setTitle(genreRequest.getTitle());

        return genre;
    }

    @Override
    public void updateGenre(Long genreId, GenreRequest genreRequest) {
        Genre genre = updateGenreFromGenreRequest(genreRetrievalService.fetchGenreEntityByGenreId(genreId), genreRequest);

        genreRepository.save(genre);
    }

    private void checkGenreExistsById(long id) {
        genreRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Genre with " + id + " not found!"));
    }

    @Override
    public void deleteGenreById(Long genreId) {
        checkGenreExistsById(genreId);

        genreRepository.deleteById(genreId);
    }
}
