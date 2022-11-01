package com.example.bootstrap;

import com.example.models.User;
import com.example.models.BlogPost;

import com.example.repositories.UserRepository;
import com.example.repositories.BlogPostRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BlogPostRepository blogPostRepository;

    public BootStrapData(UserRepository userRepository, BlogPostRepository blogPostRepository) {
        this.userRepository = userRepository;
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public void run(String... args) throws Exception {
      // User user1 = userRepository.getById(Long.valueOf(3));
      // User user2 = userRepository.getById(Long.valueOf(4));
      // User act3 = new User("billsmith", "54@j", "1");
      // User act4 = new User("josh", "josh@josh", "1");

      // BlogPost post1 = new BlogPost("easter-breakfast-of-fried-quail-eggs-on-bread-with-butter", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "https://www.foodiesfeed.com/wp-content/uploads/2019/04/easter-breakfast-of-fried-quail-eggs-on-bread-with-butter-1024x683.jpg.webp", "test content5", user1);
      // BlogPost post2 = new BlogPost("hot-shakshuka eggs poached in a sauce of tomatoes, olive oil, peppers, onion and garlic, ", "Diam vulputate ut pharetra sit amet aliquam. Integer vitae justo eget magna fermentum.", "https://www.foodiesfeed.com/wp-content/uploads/2021/01/hot-shakshuka-819x1024.jpg.webp", "test content3", user1);
      // BlogPost post3 = new BlogPost("homemade-artisan-sourdough-bread", "Auctor neque vitae tempus quam pellentesque. Diam quam nulla porttitor massa id neque. ", "https://www.foodiesfeed.com/wp-content/uploads/2021/01/homemade-artisan-sourdough-bread-819x1024.jpg.webp", "test content4", user1);
      // BlogPost post4 = new BlogPost("eggs on toast", "Risus at ultrices mi tempus imperdiet nulla malesuada pellentesque elit. Porta nibh venenatis cras sed felis eget velit aliquet sagittis. ", "https://images.pexels.com/photos/1095550/pexels-photo-1095550.jpeg?cs=srgb&dl=pexels-daria-shevtsova-1095550.jpg&fm=jpg", "test content5", user1);
      // BlogPost post5 = new BlogPost("small-donut-with-raspberry", "Duis ut diam quam nulla porttitor massa id neque aliquam. Vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. ", "https://www.foodiesfeed.com/wp-content/uploads/2021/06/small-donut-with-raspberry-on-top.jpg", "test content3", user1);
      // BlogPost post6 = new BlogPost("carrot-cake-with-fresh-fruits", "Ultricies mi eget mauris pharetra. Faucibus nisl tincidunt eget nullam non nisi est sit amet. ", "https://www.foodiesfeed.com/wp-content/uploads/2021/10/carrot-cake-with-fresh-fruits.jpg", "test content4", user2);
      // BlogPost post7 = new BlogPost("top-view-for-box-of-2-burgers-home-made", "Sagittis nisl rhoncus mattis rhoncus urna. Felis imperdiet proin fermentum leo vel orci porta non.", "https://www.foodiesfeed.com/wp-content/uploads/2019/06/top-view-for-box-of-2-burgers-home-made.jpg", "test content5", user2);
      // BlogPost post8 = new BlogPost("colorful-healthy-fresh-berries-in-a-cup", "Dolor magna eget est lorem ipsum dolor sit. Venenatis tellus in metus vulputate eu scelerisque. Justo laoreet sit amet cursus sit amet dictum sit.", "https://www.foodiesfeed.com/wp-content/uploads/2019/05/colorful-healthy-fresh-berries-in-a-cup-1.jpg", "test content3", user2);
      // BlogPost post9 = new BlogPost("mae-mu-pancakes", "Sit amet porttitor eget dolor morbi non arcu risus. In fermentum posuere urna nec tincidunt. Quam elementum pulvinar etiam non quam lacus suspendisse faucibus.", "https://www.foodiesfeed.com/wp-content/uploads/2019/04/mae-mu-pancakes.jpg", "test content4", user2);

      // user1.addPost(post1);
      // user1.addPost(post2);
      // user1.addPost(post3);
      // user1.addPost(post4);
      // user1.addPost(post5);
      // user2.addPost(post6);
      // user2.addPost(post7);
      // user2.addPost(post8);
      // user2.addPost(post9);

      // userRepository.save(user1);
      // userRepository.save(user2);
      // userRepository.save(act3);
      // userRepository.save(act4);

      // blogPostRepository.save(post1);
      // blogPostRepository.save(post2);
      // blogPostRepository.save(post3);
      // blogPostRepository.save(post4);
      // blogPostRepository.save(post5);
      // blogPostRepository.save(post6);
      // blogPostRepository.save(post7);
      // blogPostRepository.save(post8);
      // blogPostRepository.save(post9);
    }

}
