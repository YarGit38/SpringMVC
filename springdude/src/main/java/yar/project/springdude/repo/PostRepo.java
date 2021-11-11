package yar.project.springdude.repo;

import org.springframework.data.repository.CrudRepository;
import yar.project.springdude.models.Post;

public interface PostRepo extends CrudRepository<Post, Long> {

}
