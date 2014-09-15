function generateCode(code, callback) {
    window.errorLine = null;

    try {
        console.log(parser.parse(code));
    } catch(e) {
        handleError(e.message);
    };

    code = code.replace(/&/g, '&amp;')
    .replace(/ /g, '&nbsp;')
    .replace(/>/g, '&gt;')
    .replace(/</g, '&lt;')
    .replace(/"/g, '&quot;')
    .replace(/{/g, '&#123;')
    .replace(/}/g, '&#125;')
    .replace(/\|/g, '&#124;')
    .replace(/~/g, '&#126;')
    .replace(/\t/g, '<span class="tab">&nbsp;&nbsp;&nbsp;&nbsp;</span>')
    .replace(/\n/g, '<br />');

    $('.prettyprint').html(code);

    generateLines();

    callback();
}

function handleError(e) {
    var str = e.match(/Parse error on line\s[0-9]+/g);
    var line = str[0].replace('Parse error on line', '');
    line = line.replace(/\s/g, '');
    window.errorLine = line;
}

function generateLines() {
    var line,
        prettyprint;
    $('.prettyprint').each(function(index, value) {
        prettyprint = $(this);
        line = 1;

        prettyprint.prepend('<span class="line">' + line + '</span>');

        if(window.errorLine == 1) {
            $('.line', prettyprint).after('<span class="error"></span>');
        }

        line += 1;

        $('br').each(function(index, value) {
            if(line == window.errorLine) {
                $(this).after('<span class="error"></span>');
            }

            $(this).after('<span class="line">' + line + '</span>');

            if(window.errorLine != null && window.errorLine == line) {

            }

            line += 1;
        });
    });
}