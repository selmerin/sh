window.PR_SHOULD_USE_CONTINUATION = true, window.PR_TAB_WIDTH = 8, window.PR_normalizedHtml = window.PR = window.prettyPrintOne = window.prettyPrint = void
    0, window._pr_isIE6 = function () {
    var a = navigator && navigator.userAgent && navigator.userAgent.match(/\bMSIE ([678])\./);
    return a = a ? +a[1] : false, window._pr_isIE6 = function () {
        return a
    }, a
}, (function () {
    var
        a = true,
        b = null,
        c = 'break continue do else for if return while auto case char const default double enum extern float goto int long register short signed sizeof static struct switch typedef union unsigned void volatile catch class delete false import new operator private protected public this throw true try typeof ',
        d = c + 'alignof align_union asm axiom bool ' + 'concept concept_map const_cast constexpr decltype ' + 'dynamic_cast explicit export friend inline late_check ' + 'mutable namespace nullptr reinterpret_cast static_assert static_cast ' + 'template typeid typename using virtual wchar_t where ',
        e = c + 'abstract boolean byte extends final finally implements import ' + 'instanceof null native package strictfp super synchronized throws ' + 'transient ',
        f = e + 'as base by checked decimal delegate descending event ' + 'fixed foreach from group implicit in interface internal into is lock ' + 'object out override orderby params partial readonly ref sbyte sealed ' + 'stackalloc string select uint ulong unchecked unsafe ushort var ',
        g = c + 'debugger eval export function get null set undefined var with ' + 'Infinity NaN ',
        h = 'caller delete die do dump elsif eval exit foreach for goto if import last local my next no our print package redo require sub undef unless until use wantarray while BEGIN END ',
        i = 'break continue do else for if return while and as assert class def del elif except exec finally from global import in is lambda nonlocal not or pass print raise try with yield False True None ',
        j = 'break continue do else for if return while alias and begin case class def defined elsif end ensure false in module next nil not or redo rescue retry self super then true undef unless until when yield BEGIN END ',
        k = 'break continue do else for if return while case done elif esac eval fi function in local set then until ',
        l = d + f + g + h + i + j + k,
        m = (function () {
            var
                a = ['!', '!=', '!==', '#', '%', '%=', '&', '&&', '&&=', '&=', '(', '*', '*=', '+=', ',', '-=', '->', '/', '/=', ':', '::', ';', '<', '<<', '<<=', '<=', '=', '==', '===', '>', '>=', '>>', '>>=', '>>>', '>>>=', '?', '@', '[', '^', '^=', '^^', '^^=', '{', '|', '|=', '||', '||=', '~', 'break', 'case', 'continue', 'delete', 'do', 'else', 'finally', 'instanceof', 'return', 'throw', 'try', 'typeof'], b = '(?:^^|[+-]', c;
            for (c = 0; c < a.length; ++c)b += '|' + a[c].replace(/([^=<>:&a-z])/g, '\\$1');
            return b += ')\\s*', b
        })(), n = /&/g, o = /</g, p = />/g, q = /\"/g, r, s, t, u, v, w, x, y, z, A, B, C, D, E, F;

    function
        G(a) {
        return a.replace(n, '&amp;').replace(o, '&lt;').replace(p, '&gt;').replace(q, '&quot;')
    }

    function
        H(a) {
        return a.replace(n, '&amp;').replace(o, '&lt;').replace(p, '&gt;')
    }

    C = /&lt;/g, B = /&gt;/g, w = /&apos;/g, E = /&quot;/g, v = /&amp;/g, D = /&nbsp;/g;
    function
        I(a) {
        var b = a.indexOf('&'), c, d, e, f;
        if (b < 0)return a;
        for (--b; (b = a.indexOf('&#', b + 1)) >= 0;)d = a.indexOf(';', b), d >= 0 && (e = a.substring(b + 3, d), f = 10, e && e.charAt(0) === 'x' && (e = e.substring(1), f = 16), c = parseInt(e, f), isNaN(c) || (a = a.substring(0, b) + String.fromCharCode(c) + a.substring(d + 1)));
        return a.replace(C, '<').replace(B, '>').replace(w, '\'').replace(E, '\"').replace(D, ' ').replace(v, '&')
    }

    function
        J(a) {
        return'XMP' === a.tagName
    }

    u = /[\r\n]/g;
    function K(c, d) {
        var e;
        return'PRE' === c.tagName ? a : u.test(d) ? (e = '', c.currentStyle ? (e = c.currentStyle.whiteSpace) : window.getComputedStyle && (e = window.getComputedStyle(c, b).whiteSpace), !e || e === 'pre') : a
    }

    function
        L(a, b) {
        var c, d, e, f;
        switch (a.nodeType) {
            case 1:
                f = a.tagName.toLowerCase(), b.push('<', f);
                for (e = 0; e < a.attributes.length; ++e) {
                    c = a.attributes[e];
                    if (!c.specified)continue;
                    b.push(' '), L(c, b)
                }
                b.push('>');
                for (d = a.firstChild; d; d = d.nextSibling)L(d, b);
                (a.firstChild || !/^(?:br|link|img)$/.test(f)) && b.push('</', f, '>');
                break;
            case
            2:
                b.push(a.name.toLowerCase(), '=\"', G(a.value), '\"');
                break;
            case 3:
            case 4:
                b.push(H(a.nodeValue))
        }
    }

    function
        M(b) {
        var c = 0, d = false, e = false, f, g, h, i;
        for (f = 0, g = b.length; f < g; ++f) {
            h = b[f];
            if (h.ignoreCase)e = a; else if (/[a-z]/i.test(h.source.replace(/\\u[0-9a-f]{4}|\\x[0-9a-f]{2}|\\[^ux]/gi, ''))) {
                d = a, e = false;
                break
            }
        }
        function
            j(a) {
            if (a.charAt(0) !== '\\')return a.charCodeAt(0);
            switch (a.charAt(1)) {
                case'b':
                    return 8;
                case't':
                    return 9;
                case'n':
                    return 10;
                case'v':
                    return 11;
                case'f':
                    return 12;
                case'r':
                    return 13;
                case'u':
                case'x':
                    return parseInt(a.substring(2), 16) || a.charCodeAt(1);
                case'0':
                case'1':
                case'2':
                case'3':
                case'4':
                case'5':
                case'6':
                case'7':
                    return parseInt(a.substring(1), 8);
                default:
                    return a.charCodeAt(1)
            }
        }

        function
            k(a) {
            var b;
            return a < 32 ? (a < 16 ? '\\x0' : '\\x') + a.toString(16) : (b = String.fromCharCode(a), (b === '\\' || b === '-' || b === '[' || b === ']') && (b = '\\' + b), b)
        }

        function
            l(a) {
            var b = a.substring(1, a.length - 1).match(new RegExp('\\\\u[0-9A-Fa-f]{4}|\\\\x[0-9A-Fa-f]{2}|\\\\[0-3][0-7]{0,2}|\\\\[0-7]{1,2}|\\\\[\\s\\S]|-|[^-\\\\]', 'g')), c = [], d = [], e = b[0] === '^', f, g, h, i, m, n, o, p, q;
            for (h = e ? 1 : 0, m = b.length; h < m; ++h) {
                o = b[h];
                switch (o) {
                    case'\\B':
                    case'\\b':
                    case'\\D':
                    case'\\d':
                    case'\\S':
                    case'\\s':
                    case'\\W':
                    case'\\w':
                        c.push(o);
                        continue
                }
                q = j(o), h + 2 < m && '-' === b[h + 1] ? (g = j(b[h + 2]), h += 2) : (g = q), d.push([q, g]), g < 65 || q > 122 || (g < 65 || q > 90 || d.push([Math.max(65, q) | 32, Math.min(g, 90) | 32]), g < 97 || q > 122 || d.push([Math.max(97, q) & -33, Math.min(g, 122) & -33]))
            }
            d.sort(function (a, b) {
                return a[0] - b[0] || b[1] - a[1]
            }), f = [], i = [NaN, NaN];
            for (h = 0; h < d.length; ++h)p = d[h], p[0] <= i[1] + 1 ? (i[1] = Math.max(i[1], p[1])) : f.push(i = p);
            n = ['['], e && n.push('^'), n.push.apply(n, c);
            for (h = 0; h < f.length; ++h)p = f[h], n.push(k(p[0])), p[1]
                > p[0] && (p[1] + 1 > p[0] && n.push('-'), n.push(k(p[1])));
            return n.push(']'), n.join('')
        }

        function
            m(a) {
            var b = a.source.match(new RegExp('(?:\\[(?:[^\\x5C\\x5D]|\\\\[\\s\\S])*\\]|\\\\u[A-Fa-f0-9]{4}|\\\\x[A-Fa-f0-9]{2}|\\\\[0-9]+|\\\\[^ux0-9]|\\(\\?[:!=]|[\\(\\)\\^]|[^\\x5B\\x5C\\(\\)\\^]+)', 'g')), e = b.length, f = [], g, h, i, j, k;
            for (j = 0, i = 0; j < e; ++j)k = b[j], k === '(' ? ++i : '\\' === k.charAt(0) && (h = +k.substring(1), h && h <= i && (f[h] = -1));
            for (j = 1; j < f.length; ++j)-1 === f[j] && (f[j] = ++c);
            for (j = 0, i = 0; j < e; ++j)k = b[j], k === '(' ? (++i, f[i] === void
                0 && (b[j] = '(?:')) : '\\' === k.charAt(0) && (h = +k.substring(1), h && h <= i && (b[j] = '\\' + f[i]));
            for (j = 0, i = 0; j < e; ++j)'^' === b[j] && '^' !== b[j + 1] && (b[j] = '');
            if (a.ignoreCase && d)for (j = 0; j < e; ++j)k = b[j], g = k.charAt(0), k.length >= 2 && g === '[' ? (b[j] = l(k)) : g !== '\\' && (b[j] = k.replace(/[a-zA-Z]/g, function (a) {
                var
                    b = a.charCodeAt(0);
                return'[' + String.fromCharCode(b & -33, b | 32) + ']'
            }));
            return b.join('')
        }

        i = [];
        for (f = 0, g = b.length; f < g; ++f) {
            h = b[f];
            if (h.global || h.multiline)throw new
                Error('' + h);
            i.push('(?:' + m(h) + ')')
        }
        return new RegExp(i.join('|'), e ? 'gi' : 'g')
    }

    r = b;
    function
        N(a) {
        var c, d, e, f;
        b === r && (f = document.createElement('PRE'), f.appendChild(document.createTextNode('<!DOCTYPE foo PUBLIC \"foo bar\">\n<foo />')), r = !/</.test(f.innerHTML));
        if (r)return d = a.innerHTML, J(a) ? (d = H(d)) : K(a, d) || (d = d.replace(/(<br\s*\/?>)[\r\n]+/g, '$1').replace(/(?:[\r\n]+[ \t]*)+/g, ' ')), d;
        e = [];
        for (c = a.firstChild; c; c = c.nextSibling)L(c, e);
        return e.join('')
    }

    function
        O(a) {
        var c = 0;
        return function (d) {
            var e = b, f = 0, g, h, i, j;
            for (h = 0, i = d.length; h < i; ++h) {
                g = d.charAt(h);
                switch (g) {
                    case'	':
                        e || (e = []), e.push(d.substring(f, h)), j = a - c % a, c += j;
                        for (; j >= 0; j -= '                '.length)e.push('                '.substring(0, j));
                        f = h + 1;
                        break;
                    case'\n':
                        c = 0;
                        break;
                    default:
                        ++c
                }
            }
            return e ? (e.push(d.substring(f)), e.join('')) : d
        }
    }

    z = new
        RegExp('[^<]+|<!--[\\s\\S]*?-->|<!\\[CDATA\\[[\\s\\S]*?\\]\\]>|</?[a-zA-Z](?:[^>\"\']|\'[^\']*\'|\"[^\"]*\")*>|<', 'g'), A = /^<\!--/, y = /^<!\[CDATA\[/, x = /^<br\b/i, F = /^<(\/?)([a-zA-Z][a-zA-Z0-9]*)/;
    function
        P(a) {
        var b = a.match(z), c = [], d = 0, e = [], f, g, h, i, j, k, l, m;
        if (b)for (g = 0, k = b.length; g < k; ++g) {
            j = b[g];
            if (j.length > 1 && j.charAt(0) === '<') {
                if (A.test(j))continue;
                if (y.test(j))c.push(j.substring(9, j.length - 3)), d += j.length - 12; else if (x.test(j))c.push('\n'), ++d; else if (j.indexOf('nocode') >= 0 && Q(j)) {
                    l = (j.match(F))[2], f = 1;
                    for (h = g + 1; h < k; ++h) {
                        m = b[h].match(F);
                        if (m && m[2] === l)if (m[1] === '/') {
                            if (--f === 0)break
                        } else++f
                    }
                    h < k ? (e.push(d, b.slice(g, h + 1).join('')), g = h) : e.push(d, j)
                } else
                    e.push(d, j)
            } else i = I(j), c.push(i), d += i.length
        }
        return{source: c.join(''), tags: e}
    }

    function
        Q(a) {
        return!!a.replace(/\s(\w+)\s*=\s*(?:\"([^\"]*)\"|'([^\']*)'|(\S+))/g, ' $1=\"$2$3$4\"').match(/[cC][lL][aA][sS][sS]=\"[^\"]*\bnocode\b/)
    }

    function
        R(a, b, c, d) {
        var e;
        if (!b)return;
        e = {source: b, basePos: a}, c(e), d.push.apply(d, e.decorations)
    }

    function
        S(a, c) {
        var d = {}, e, f, g, h;
        return(function () {
            var e = a.concat(c), f = [], g = {}, i, j, k, l, m, n, o;
            for (j = 0, l = e.length; j < l; ++j) {
                m = e[j], o = m[3];
                if (o)for (i = o.length; --i >= 0;)d[o.charAt(i)] = m;
                n = m[1], k = '' + n, g.hasOwnProperty(k) || (f.push(n), g[k] = b)
            }
            f.push(/[\0-\uffff]/), h = M(f)
        })(), f = c.length, g = /\S/, e = function (a) {
            var
                b = a.source, g = a.basePos, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y;
            i = [g, 'pln'], s = 0, y = b.match(h) || [], u = {};
            for (v = 0, q = y.length; v < q; ++v) {
                w = y[v], t = u[w], p = void
                    0;
                if (typeof t === 'string')n = false; else {
                    r = d[w.charAt(0)];
                    if (r)p = w.match(r[1]), t = r[0]; else {
                        for (m = 0; m < f; ++m) {
                            r = c[m], p = w.match(r[1]);
                            if (p) {
                                t = r[0];
                                break
                            }
                        }
                        p || (t = 'pln')
                    }
                    n = t.length >= 5 && 'lang-' === t.substring(0, 5), n && !(p && typeof
                        p[1] === 'string') && (n = false, t = 'src'), n || (u[w] = t)
                }
                x = s, s += w.length, n ? (j = p[1], l = w.indexOf(j), k = l + j.length, p[2] && (k = w.length - p[2].length, l = k - j.length), o = t.substring(5), R(g + x, w.substring(0, l), e, i), R(g + x + l, j, W(o, j), i), R(g + x + k, w.substring(k), e, i)) : i.push(g + x, t)
            }
            a.decorations = i
        }, e
    }

    function
        T(a) {
        var c = [], d = [], e, f;
        return a.tripleQuotedStrings ? c.push(['str', /^(?:\'\'\'(?:[^\'\\]|\\[\s\S]|\'{1,2}(?=[^\']))*(?:\'\'\'|$)|\"\"\"(?:[^\"\\]|\\[\s\S]|\"{1,2}(?=[^\"]))*(?:\"\"\"|$)|\'(?:[^\\\']|\\[\s\S])*(?:\'|$)|\"(?:[^\\\"]|\\[\s\S])*(?:\"|$))/, b, '\'\"']) : a.multiLineStrings ? c.push(['str', /^(?:\'(?:[^\\\']|\\[\s\S])*(?:\'|$)|\"(?:[^\\\"]|\\[\s\S])*(?:\"|$)|\`(?:[^\\\`]|\\[\s\S])*(?:\`|$))/, b, '\'\"`']) : c.push(['str', /^(?:\'(?:[^\\\'\r\n]|\\.)*(?:\'|$)|\"(?:[^\\\"\r\n]|\\.)*(?:\"|$))/, b, '\"\'']), a.verbatimStrings && d.push(['str', /^@\"(?:[^\"]|\"\")*(?:\"|$)/, b]), a.hashComments && (a.cStyleComments ? (c.push(['com', /^#(?:(?:define|elif|else|endif|error|ifdef|include|ifndef|line|pragma|undef|warning)\b|[^\r\n]*)/, b, '#']), d.push(['str', /^<(?:(?:(?:\.\.\/)*|\/?)(?:[\w-]+(?:\/[\w-]+)+)?[\w-]+\.h|[a-z]\w*)>/, b])) : c.push(['com', /^#[^\r\n]*/, b, '#'])), a.cStyleComments && (d.push(['com', /^\/\/[^\r\n]*/, b]), d.push(['com', /^\/\*[\s\S]*?(?:\*\/|$)/, b])), a.regexLiterals && (e = '/(?=[^/*])(?:[^/\\x5B\\x5C]|\\x5C[\\s\\S]|\\x5B(?:[^\\x5C\\x5D]|\\x5C[\\s\\S])*(?:\\x5D|$))+/', d.push(['lang-regex', new
            RegExp('^' + m + '(' + e + ')')])), f = a.keywords.replace(/^\s+|\s+$/g, ''), f.length && d.push(['kwd', new
            RegExp('^(?:' + f.replace(/\s+/g, '|') + ')\\b'), b]), c.push(['pln', /^\s+/, b, ' \r\n	\xa0']), d.push(['lit', /^@[a-z_$][a-z_$@0-9]*/i, b], ['typ', /^@?[A-Z]+[a-z][A-Za-z_$@0-9]*/, b], ['pln', /^[a-z_$][a-z_$@0-9]*/i, b], ['lit', new
            RegExp('^(?:0x[a-f0-9]+|(?:\\d(?:_\\d+)*\\d*(?:\\.\\d*)?|\\.\\d\\+)(?:e[+\\-]?\\d+)?)[a-z]*', 'i'), b, '0123456789'], ['pun', /^.[^\s\w\.$@\'\"\`\/\#]*/, b]), S(c, d)
    }

    s = T({keywords: l, hashComments: a, cStyleComments: a, multiLineStrings: a, regexLiterals: a});
    function
        U(c) {
        var d = c.source, e = c.extractedTags, f = c.decorations, g = [], h = 0, i = b, j = b, k = 0, l = 0, m = O(window.PR_TAB_WIDTH), n = /([\r\n ]) /g, o = /(^| ) /gm, p = /\r\n?|\n/g, q = /[ \r\n]$/, r = a, s;

        function
            t(a) {
            var c, e;
            a > h && (i && i !== j && (g.push('</span>'), i = b), !i && j && (i = j, g.push('<span class=\"', i, '\">')), c = H(m(d.substring(h, a))).replace(r ? o : n, '$1&nbsp;'), r = q.test(c), e = window._pr_isIE6() ? '&nbsp;<br />' : '<br />', g.push(c.replace(p, e)), h = a)
        }

        while (a) {
            k < e.length ? l < f.length ? (s = e[k] <= f[l]) : (s = a) : (s = false);
            if (s)t(e[k]), i && (g.push('</span>'), i = b), g.push(e[k + 1]), k += 2; else if (l < f.length)t(f[l]), j = f[l + 1], l += 2; else break
        }
        t(d.length), i && g.push('</span>'), c.prettyPrintedHtml = g.join('')
    }

    t = {};
    function
        V(a, b) {
        var c, d;
        for (d = b.length; --d >= 0;)c = b[d], t.hasOwnProperty(c) ? 'console'in window && console.warn('cannot override language handler %s', c) : (t[c] = a)
    }

    function
        W(a, b) {
        return a && t.hasOwnProperty(a) || (a = /^\s*</.test(b) ? 'default-markup' : 'default-code'), t[a]
    }

    V(s, ['default-code']), V(S([], [
        ['pln', /^[^<?]+/],
        ['dec', /^<!\w[^>]*(?:>|$)/],
        ['com', /^<\!--[\s\S]*?(?:-\->|$)/],
        ['lang-', /^<\?([\s\S]+?)(?:\?>|$)/],
        ['lang-', /^<%([\s\S]+?)(?:%>|$)/],
        ['pun', /^(?:<[%?]|[%?]>)/],
        ['lang-', /^<xmp\b[^>]*>([\s\S]+?)<\/xmp\b[^>]*>/i],
        ['lang-js', /^<script\b[^>]*>([\s\S]*?)(<\/script\b[^>]*>)/i],
        ['lang-css', /^<style\b[^>]*>([\s\S]*?)(<\/style\b[^>]*>)/i],
        ['lang-in.tag', /^(<\/?[a-z][^<>]*>)/i]
    ]), ['default-markup', 'htm', 'html', 'mxml', 'xhtml', 'xml', 'xsl']), V(S([
        ['pln', /^[\s]+/, b, ' 	\r\n'],
        ['atv', /^(?:\"[^\"]*\"?|\'[^\']*\'?)/, b, '\"\'']
    ], [
        ['tag', /^^<\/?[a-z](?:[\w.:-]*\w)?|\/?>$/i],
        ['atn', /^(?!style[\s=]|on)[a-z](?:[\w:-]*\w)?/i],
        ['lang-uq.val', /^=\s*([^>\'\"\s]*(?:[^>\'\"\s\/]|\/(?=\s)))/],
        ['pun', /^[=<>\/]+/],
        ['lang-js', /^on\w+\s*=\s*\"([^\"]+)\"/i],
        ['lang-js', /^on\w+\s*=\s*\'([^\']+)\'/i],
        ['lang-js', /^on\w+\s*=\s*([^\"\'>\s]+)/i],
        ['lang-css', /^style\s*=\s*\"([^\"]+)\"/i],
        ['lang-css', /^style\s*=\s*\'([^\']+)\'/i],
        ['lang-css', /^style\s*=\s*([^\"\'>\s]+)/i]
    ]), ['in.tag']), V(S([], [
        ['atv', /^[\s\S]+/]
    ]), ['uq.val']), V(T({keywords: d, hashComments: a, cStyleComments: a}), ['c', 'cc', 'cpp', 'cxx', 'cyc', 'm']), V(T({keywords: 'null true false'}), ['json']), V(T({keywords: f, hashComments: a, cStyleComments: a, verbatimStrings: a}), ['cs']), V(T({keywords: e, cStyleComments: a}), ['java']), V(T({keywords: k, hashComments: a, multiLineStrings: a}), ['bsh', 'csh', 'sh']), V(T({keywords: i, hashComments: a, multiLineStrings: a, tripleQuotedStrings: a}), ['cv', 'py']), V(T({keywords: h, hashComments: a, multiLineStrings: a, regexLiterals: a}), ['perl', 'pl', 'pm']), V(T({keywords: j, hashComments: a, multiLineStrings: a, regexLiterals: a}), ['rb']), V(T({keywords: g, cStyleComments: a, regexLiterals: a}), ['js']), V(S([], [
        ['str', /^[\s\S]+/]
    ]), ['regex']);
    function
        X(a) {
        var b = a.sourceCodeHtml, c = a.langExtension, d, e;
        a.prettyPrintedHtml = b;
        try {
            e = P(b), d = e.source, a.source = d, a.basePos = 0, a.extractedTags = e.tags, W(c, d)(a), U(a)
        } catch (f) {
            'console'in
            window && (console.log(f), console.trace())
        }
    }

    function Y(a, b) {
        var c = {sourceCodeHtml: a, langExtension: b};
        return X(c), c.prettyPrintedHtml
    }

    function
        Z(c) {
        var d = window._pr_isIE6(), e = d === 6 ? '\r\n' : '\r', f = [document.getElementsByTagName('pre'), document.getElementsByTagName('code'), document.getElementsByTagName('xmp')], g = [], h, i, j, k, l, m;
        for (i = 0; i < f.length; ++i)for (j = 0, l = f[i].length; j < l; ++j)g.push(f[i][j]);
        f = b, h = Date, h.now || (h = {now: function () {
            return(new
                Date).getTime()
        }}), k = 0;
        function n() {
            var b = window.PR_SHOULD_USE_CONTINUATION ? h.now() + 250 : Infinity, d, e, f, i, j;
            for (; k < g.length && h.now() < b; ++k) {
                e = g[k];
                if (e.className && e.className.indexOf('prettyprint') >= 0) {
                    f = e.className.match(/\blang-(\w+)\b/), f && (f = f[1]), i = false;
                    for (j = e.parentNode; j; j = j.parentNode)if ((j.tagName === 'pre' || j.tagName === 'code' || j.tagName === 'xmp') && j.className && j.className.indexOf('prettyprint') >= 0) {
                        i = a;
                        break
                    }
                    i || (d = N(e), d = d.replace(/(?:\r\n?|\n)$/, ''), m = {sourceCodeHtml: d, langExtension: f, sourceNode: e}, X(m), o())
                }
            }
            k < g.length ? setTimeout(n, 250) : c && c()
        }

        function
            o() {
            var a = m.prettyPrintedHtml, b, c, f, g, h, i, j, k;
            if (!a)return;
            f = m.sourceNode;
            if (!J(f))f.innerHTML = a; else {
                k = document.createElement('PRE');
                for (g = 0; g < f.attributes.length; ++g)b = f.attributes[g], b.specified && (c = b.name.toLowerCase(), c === 'class' ? (k.className = b.value) : k.setAttribute(b.name, b.value));
                k.innerHTML = a, f.parentNode.replaceChild(k, f), f = k
            }
            if (d && f.tagName === 'PRE') {
                j = f.getElementsByTagName('br');
                for (h = j.length; --h >= 0;)i = j[h], i.parentNode.replaceChild(document.createTextNode(e), i)
            }
        }

        n()
    }

    window.PR_normalizedHtml = L, window.prettyPrintOne = Y, window.prettyPrint = Z, window.PR = {combinePrefixPatterns: M, createSimpleLexer: S, registerLangHandler: V, sourceDecorator: T, PR_ATTRIB_NAME: 'atn', PR_ATTRIB_VALUE: 'atv', PR_COMMENT: 'com', PR_DECLARATION: 'dec', PR_KEYWORD: 'kwd', PR_LITERAL: 'lit', PR_NOCODE: 'nocode', PR_PLAIN: 'pln', PR_PUNCTUATION: 'pun', PR_SOURCE: 'src', PR_STRING: 'str', PR_TAG: 'tag', PR_TYPE: 'typ'}
})()