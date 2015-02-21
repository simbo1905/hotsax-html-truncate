

# hotsax-html-truncate

Quick demo of using the HotSax HTML SAX Parser to extract a truncated review of some HTML used to answer this question on GitHub:

http://stackoverflow.com/questions/28567039/java-library-to-truncate-html-strings/28617834#28617834

I used HotSax a few years ago as a low overhead, "stream-link" way, to extract some infomation from html (i.e. only the text not any markup). Whilst there are excellent tools like jsoup for handling HTML such tools create the full DOM up into objects before you can extract what you want (doing an excellent job of handling broken HTML such as missing or poorly balanced tags). HotSax in contrast just gives you a stream of start/end/text events and it is up to you to collect the infomation you need. So you could, say, collect all `<a>` and all text between tags. These could be pushed to a lucene engine to index the contents of the pages and the `<a>` tags pushed to the spider which is pulling pages to index. 
