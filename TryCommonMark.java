import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

class TryCommonMark {
	//this class can be defined anywhere in the file
	static class WordCountVisitor extends AbstractVisitor {
    	int wordCount = 0;

    	@Override
    	public void visit(Text text) {
        	// This is called for all Text nodes. Override other visit methods for other node types.

        	// Count words (this is just an example, don't actually do it this way for various reasons).
        	wordCount += text.getLiteral().split("\\W+").length;

        	// Descend into children (could be omitted in this case because Text nodes don't have children).
        	visitChildren(text);
    	}
	}

	static class LinkCountVisitor extends AbstractVisitor {
		int linkCount = 0;
		public void visit(Link link) {
			if (!link.getDestination().equals("")) {
				linkCount++;
			}

			visitChildren(link);
		}
	}
	public static void main(String[] args) {
		Parser parser = Parser.builder().build();
		Node link = parser.parse("[hello](google.com)\n[sauce](sauce.com)\n[test](test.com)");
		LinkCountVisitor visitor = new LinkCountVisitor();
		link.accept(visitor);
		System.out.println(visitor.linkCount);
	}
}
