package net.jps.atom.hopper.abdera;

import com.rackspace.cloud.commons.util.http.HttpStatusCode;
import net.jps.atom.hopper.adapter.FeedPublisher;
import net.jps.atom.hopper.adapter.FeedSource;
import net.jps.atom.hopper.adapter.request.DeleteEntryRequest;
import net.jps.atom.hopper.adapter.request.GetEntryRequest;
import net.jps.atom.hopper.adapter.request.PostEntryRequest;
import net.jps.atom.hopper.adapter.request.PutEntryRequest;
import net.jps.atom.hopper.config.v1_0.FeedConfiguration;
import net.jps.atom.hopper.response.AdapterResponse;
import net.jps.atom.hopper.response.EmptyBody;
import net.jps.atom.hopper.response.FeedSourceAdapterResponse;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.parser.stax.FOMEntry;
import org.apache.abdera.protocol.server.RequestContext;
import org.apache.abdera.protocol.server.ResponseContext;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Enclosed.class)
public class FeedAdapterTest {

    public static class WhenPostingEntryToFeed extends TestParent {

        @Test
        public void shouldReturnUnsupportedMethodGivenNoFeedPublisher() {
            FeedAdapter feedAdapter = feedAdapter(false);
            ResponseContext responseContext = feedAdapter.postEntry(REQUEST_CONTEXT);
            assertEquals("Should respond with " + STATUS_CODE_UNSUPPORTED_METHOD, STATUS_CODE_UNSUPPORTED_METHOD, responseContext.getStatus());
        }

        @Test
        public void shouldReturnEntryResponse() throws IOException {
            FeedAdapter feedAdapter = feedAdapter(true);
            when(feedPublisher.postEntry(any(PostEntryRequest.class))).thenReturn(adapterResponseForEntry());
            ResponseContext responseContext = feedAdapter.postEntry(REQUEST_CONTEXT);
            assertEquals("Should respond with 200", 200, responseContext.getStatus());
        }

        @Test
        public void shouldReturnServerErrorOnFeedPublisherException() throws IOException {
            FeedAdapter feedAdapter = feedAdapter(true);
            when(feedPublisher.postEntry(any(PostEntryRequest.class))).thenThrow(new RuntimeException());
            ResponseContext responseContext = feedAdapter.postEntry(REQUEST_CONTEXT);
            assertEquals("Should respond with 500", 500, responseContext.getStatus());
        }
    }

    public static class WhenPuttingEntryToFeed extends TestParent {

        @Test
        public void shouldReturnUnsupportedMethodGivenNoFeedPublisher() {
            FeedAdapter feedAdapter = feedAdapter(false);
            ResponseContext responseContext = feedAdapter.putEntry(REQUEST_CONTEXT);
            assertEquals("Should respond with " + STATUS_CODE_UNSUPPORTED_METHOD, STATUS_CODE_UNSUPPORTED_METHOD, responseContext.getStatus());
        }

        @Test
        public void shouldReturnEntryResponse() throws IOException {
            FeedAdapter feedAdapter = feedAdapter(true);
            when(feedPublisher.putEntry(any(PutEntryRequest.class))).thenReturn(adapterResponseForEntry());
            ResponseContext responseContext = feedAdapter.putEntry(REQUEST_CONTEXT);
            assertEquals("Should respond with 200", 200, responseContext.getStatus());
        }

        @Test
        public void shouldReturnServerErrorOnFeedPublisherException() throws IOException {
            FeedAdapter feedAdapter = feedAdapter(true);
            when(feedPublisher.putEntry(any(PutEntryRequest.class))).thenThrow(new RuntimeException());
            ResponseContext responseContext = feedAdapter.putEntry(REQUEST_CONTEXT);
            assertEquals("Should respond with 500", 500, responseContext.getStatus());
        }
    }

    public static class WhenDeletingEntryFromFeed extends TestParent {

        @Test
        public void shouldReturnUnsupportedMethodGivenNoFeedPublisher() {
            FeedAdapter feedAdapter = feedAdapter(false);
            ResponseContext responseContext = feedAdapter.deleteEntry(REQUEST_CONTEXT);
            assertEquals("Should respond with " + STATUS_CODE_UNSUPPORTED_METHOD, STATUS_CODE_UNSUPPORTED_METHOD, responseContext.getStatus());
        }

        @Test
        public void shouldReturnEntryResponse() throws IOException {
            FeedAdapter feedAdapter = feedAdapter(true);
            when(feedPublisher.deleteEntry(any(DeleteEntryRequest.class))).thenReturn(adapterResponseForEmptyBody(HttpStatusCode.OK));
            ResponseContext responseContext = feedAdapter.deleteEntry(REQUEST_CONTEXT);
            assertEquals("Should respond with 204", 204, responseContext.getStatus());
        }

        @Test
        public void shouldReturnServerErrorOnFeedPublisherException() throws IOException {
            FeedAdapter feedAdapter = feedAdapter(true);
            when(feedPublisher.deleteEntry(any(DeleteEntryRequest.class))).thenThrow(new RuntimeException());
            ResponseContext responseContext = feedAdapter.deleteEntry(REQUEST_CONTEXT);
            assertEquals("Should respond with 500", 500, responseContext.getStatus());
        }
    }

    public static class WhenGettingEntryFromFeed extends TestParent {
        @Test
        public void shouldReturnEntryResponse() throws IOException {
            FeedAdapter feedAdapter = feedAdapter(true);
            when(feedSource.getEntry(any(GetEntryRequest.class))).thenReturn(adapterResponseForEntry());
            ResponseContext responseContext = feedAdapter.getEntry(REQUEST_CONTEXT);
            assertEquals("Should respond with 200", 200, responseContext.getStatus());
        }

        @Test
        public void shouldReturnServerErrorOnFeedSourceException() throws IOException {
            FeedAdapter feedAdapter = feedAdapter(true);
            when(feedSource.getEntry(any(GetEntryRequest.class))).thenThrow(new RuntimeException());
            ResponseContext responseContext = feedAdapter.getEntry(REQUEST_CONTEXT);
            assertEquals("Should respond with 500", 500, responseContext.getStatus());
        }
    }


    @Ignore
    public static class TestParent {
        static final int STATUS_CODE_UNSUPPORTED_METHOD = 415;

        final RequestContext REQUEST_CONTEXT = requestContext();

        FeedConfiguration feedConfiguration;
        FeedSource feedSource;
        FeedPublisher feedPublisher;

        public FeedAdapter feedAdapter(boolean supportsPublishing) {
            feedConfiguration = mock(FeedConfiguration.class);
            feedSource = mock(FeedSource.class);
            if (supportsPublishing) {
                feedPublisher = mock(FeedPublisher.class);
            } else {
                feedPublisher = null;
            }

            final FeedAdapter target = new FeedAdapter(feedConfiguration, feedSource, feedPublisher);
            return target;
        }

        public Entry entry() {
            final FOMEntry entry = new FOMEntry();
            entry.setId(UUID.randomUUID().toString());
            entry.setContent("testing");
            return entry;
        }

        public AdapterResponse<Entry> adapterResponseForEntry() {
            return new FeedSourceAdapterResponse<Entry>(entry());
        }

        public AdapterResponse<EmptyBody> adapterResponseForEmptyBody(HttpStatusCode status) {
            return new FeedSourceAdapterResponse<EmptyBody>(EmptyBody.getInstance(), status, null);
        }

        public RequestContext requestContext() {
            RequestContext context = mock(RequestContext.class);
            Document document = mock(Document.class);
            try {
                when(context.getDocument()).thenReturn(document);
            } catch (IOException e) {
                fail("Unexpected exception in test");
            }
            when(document.getRoot()).thenReturn(entry());

            return context;
        }

    }


}