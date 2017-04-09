package br.tiagohm.markdownview.css.styles;

public class Github extends Bootstrap
{
    public Github()
    {
        addRule("body", "line-height: 1.6", "padding: 10px 30px","background-color: #424242");
        addRule("h1", "font-size: 28px");
        addRule("h2", "font-size: 24px");
        addRule("h3", "font-size: 18px");
        addRule("h4", "font-size: 16px");
        addRule("h5", "font-size: 14px");
        addRule("h6", "font-size: 14px");
        addRule("pre", "padding: 6px 10px", "border: 0", "border-radius: 3px", "background-color: #f6f8fa");
        addRule("pre code", "line-height: 1.45", "background-color: #f6f8fa");
        addRule("table tr:nth-child(2n)", "background-color: #f6f8fa");
        addRule("table th", "padding: 6px 13px", "border: 1px solid #dfe2e5");
        addRule("table td", "padding: 6px 13px", "border: 1px solid #dfe2e5");
        addRule("kbd", "color: #444d56", "font-family: Consolas, \"Liberation Mono\", Menlo, Courier, monospace",
                "background-color: #fcfcfc", "border: solid 1px #c6cbd1",
                "border-bottom-color: #959da5", "border-radius: 3px", "box-shadow: inset 0 -1px 0 #959da5");

        addRule(".footnotes li p:last-of-type", "display: inline");

        //Highlight.js

        addRule(".hljs-comment", "color: #8e908c");
        addRule(".hljs-quote", "color: #8e908c");

        addRule(".hljs-variable", "color: #c82829");
        addRule(".hljs-template-variable", "color: #c82829");
        addRule(".hljs-tag", "color: #c82829");
        addRule(".hljs-name", "color: #c82829");
        addRule(".hljs-selector-id", "color: #c82829");
        addRule(".hljs-selector-class", "color: #c82829");
        addRule(".hljs-regexp", "color: #c82829");
        addRule(".hljs-deletion", "color: #c82829");

        addRule(".hljs-number", "color: #f5871f");
        addRule(".hljs-built_in", "color: #f5871f");
        addRule(".hljs-builtin-name", "color: #f5871f");
        addRule(".hljs-literal", "color: #f5871f");
        addRule(".hljs-type", "color: #f5871f");
        addRule(".hljs-params", "color: #f5871f");
        addRule(".hljs-meta", "color: #f5871f");
        addRule(".hljs-link", "color: #f5871f");

        addRule(".hljs-attribute", "color: #eab700");

        addRule(".hljs-string", "color: #718c00");
        addRule(".hljs-symbol", "color: #718c00");
        addRule(".hljs-bullet", "color: #718c00");
        addRule(".hljs-addition", "color: #718c00");

        addRule(".hljs-title", "color: #4271ae");
        addRule(".hljs-section", "color: #4271ae");

        addRule(".hljs-keyword", "color: #8959a8");
        addRule(".hljs-selector-tag", "color: #8959a8");
    }
}
