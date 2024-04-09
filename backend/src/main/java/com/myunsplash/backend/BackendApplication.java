package com.myunsplash.backend;

import java.util.function.Function;
import java.util.Map;
import java.util.List;

import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@SpringBootApplication
public class BackendApplication {

        @Autowired
        private PhotosRepository photosRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

        @Bean
        public Function<String, List<Photo>> images() {
            return id -> photosRepository.getImages(id);
        }

        @Bean
        public Function<Map<String, Object>, List<Photo>> search() {
            return request -> {
              var q = (String) request.getOrDefault("q", "");
              return photosRepository.searchImages(q);
            };
        }

        @Bean
        public Function<Map<String, Object>, Photo> saveImage() {
            return (request) -> {
              var label = (String) request.get("label");
              var url = (String) request.get("url");
              if (label.length() == 0 || url.length() == 0 || url == null || label == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "label and url are required");
              }
              var obj = new Photo();
              obj.setLabel(label);
              obj.setUrl(url);
              var photo = photosRepository.save(obj);
              return photo;
            };
        }

        @Bean
        public Consumer<String> deleteImage() {
            return (id) -> {
              var result = photosRepository.delete(id);
              if (result == null) {
                  throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
              }
            };
        }
}
