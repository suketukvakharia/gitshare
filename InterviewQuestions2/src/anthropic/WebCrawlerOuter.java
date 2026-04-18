package anthropic;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Test;

public class WebCrawlerOuter {

  /**
   * Build a web crawler that performs breadth-first search (BFS) traversal starting from a seed
   * URL, discovering and fetching all reachable pages within the same domain. Implement concurrent
   * fetching using multithreading to improve crawling speed while maintaining thread safety for
   * shared data structures like the URL queue and visited set. For example, starting from
   * https://example.com, the crawler should visit https://example.com/about and
   * https://example.com/contact but skip https://other.com.
   *
   * Input:
   *
   * start_url = "https://example.com"
   * max_threads = 4
   *
   * Page structure:
   * example.com → [example.com/about, example.com/blog]
   * example.com/about → [example.com/contact]
   * example.com/blog → [example.com/about, external.com]
   * Output:
   *
   * Crawled URLs (in BFS order):
   * 1. https://example.com
   * 2. https://example.com/about
   * 3. https://example.com/blog
   * 4. https://example.com/contact
   *
   * Total pages crawled: 4
   * (external.com skipped - different domain)
   *
   * Explanation: The crawler uses BFS to visit pages level-by-level, employs 4 threads for
   * concurrent fetching, and only crawls URLs within the same domain while tracking visited pages
   * to avoid duplicates.
   *
   * Constraints:
   *
   * Only crawl URLs within the same domain as the starting URL
   * Use BFS traversal order for discovering URLs
   * Implement thread-safe access to shared queue and visited set
   * Handle concurrent URL fetching with multiple threads
   * Avoid crawling the same URL twice
   *
   * The core of the problem seems to be a BFS traversal over the URL graph.
   *
   * From Data structures we will need a concurrent queue. FIFO is better but not strictly needed
   * for the requirements.
   * Handling concurrent access to data structures is important.
   * Inserts into the queue might need locking.
   */
  private class WebCrawler {

    private static final int MAX_QUEUE_WAIT_TIME = 5000;
    private static final int WORKER_COUNT = 4;
    private final LinkFetcher linkFetcher;
    ExecutorService workers;

    public WebCrawler(LinkFetcher linkFetcher) {
      this.workers = Executors.newFixedThreadPool(WORKER_COUNT);
      this.linkFetcher = linkFetcher;
    }

    public static String getDomainName(String url) throws URISyntaxException {
      URI uri = new URI(url);
      String domain = uri.getHost();
      return domain;
    }

    public void crawl(String url) throws Exception {
      // visited urls & queue
      Set<String> visitedUrls = ConcurrentHashMap.newKeySet();
      ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
      String startingDomain = getDomainName(url);

      // enqueue first
      queue.add(url);

      // start the executors
      List<Callable<Void>> tasks = new ArrayList<>();
      for (int i = 0; i < WORKER_COUNT; i++) {
        WebCrawlerWorker worker = new WebCrawlerWorker(queue, visitedUrls, linkFetcher, startingDomain);
        tasks.add(worker);
      }
      workers.invokeAll(tasks);
    }

    private class WebCrawlerWorker implements Callable<Void> {
      private final ConcurrentLinkedQueue<String> queue;
      private final Set<String> visitedUrls;
      private final LinkFetcher linkFetcher;
      private final String startingDomain;

      public WebCrawlerWorker(ConcurrentLinkedQueue<String> queue, Set<String> visitedUrls,
          LinkFetcher linkFetcher, String startingDomain) {
        this.queue = queue;
        this.visitedUrls = visitedUrls;
        this.linkFetcher = linkFetcher;
        this.startingDomain = startingDomain;
      }

      public Void call() throws Exception {

        try {
          while (true) {
            // try to get item from queue with max wait time, if fail then exit
            String url = this.queue.poll();
            System.err.println("Got URL:" + url);

            if (url == null) {
              Thread.sleep(MAX_QUEUE_WAIT_TIME);
              url = this.queue.poll();
            }

            if (url == null) {
              break; // done
            }

            // check visited
            if (visitedUrls.contains(url)) {
              System.err.println("Already visited link:" + url);
              continue;
            }

            // check domain*
            String domain = getDomainName(url);
            System.err.println("Got domain:" + domain + " for url:" + url);
            if (!startingDomain.equals(domain)) {
              System.err.println("Out of domain link:" + url);
              continue;
            }

            // use link fetcher to get linked urls*
            List<String> links = this.linkFetcher.getLinks(url);
            System.err.println("Got links:" + links);
            // enqueue all the links
            if (links != null) {
              for (String link : links) {
                this.queue.add(link);
              }
            }

            System.err.println("Processed link:" + url);
          }
        } catch (Exception e) {
          System.err.println(e.getMessage());
          e.printStackTrace();
        }

        return null;
      }
    }
  }

  /**
   * Rsponsible for getting links for each URL, for now it just uses static map*
   */
  private class LinkFetcher {

    Map<String, List<String>> urlToLinks = new HashMap<>();

    public LinkFetcher() {
      this.urlToLinks = new HashMap<>();
    }

    public List<String> getLinks(String url) {
      return this.urlToLinks.get(url);
    }

    public void registerLinks(String url, List<String> links) {
      this.urlToLinks.put(url, links);
    }
  }


  @Test
  public void test1() throws Exception {

    /**
     *    * example.com → [example.com/about, example.com/blog]
     *    * example.com/about → [example.com/contact]
     *    * example.com/blog → [example.com/about, external.com]
     */
    LinkFetcher linkFetcher = new LinkFetcher();
    linkFetcher.registerLinks("https://www.example.com", List.of("https://www.example.com/about", "https://www.example.com/blog"));
    linkFetcher.registerLinks("https://www.example.com/about", List.of("https://www.example.com/contact"));
    linkFetcher.registerLinks("https://www.example.com/blog", List.of("https://www.example.com/about", "https://www.external.com"));

    // crawler
    WebCrawler crawler = new WebCrawler(linkFetcher);
    crawler.crawl("https://www.example.com");
  }

}
