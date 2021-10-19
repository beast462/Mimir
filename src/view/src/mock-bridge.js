if (navigator.userAgent.includes('Chrome'))
  window.bridge = {
    absoluteSearch() {
      return { id: 158299840, content: 'get' };
    },
    advanceSearch() {
      return [{ id: 158299840, content: 'get' }];
    },
    getWord() {
      return {
        id: 158299840,
        content: 'get',
        pronunciation: 'get',
        definitions: [
          {
            id: 200199282,
            definition: 'b?t ???c (c�, th� r?ng...); ?em v?, thu v? (th�c...)',
            wordType: 19,
            examples: [],
          },
          {
            id: 215739633,
            definition: 't�m ra, t�nh ra',
            wordType: 16,
            examples: [],
          },
          {
            id: 218077985,
            definition: 'hi?u ???c, n?m ???c (�...)',
            wordType: 16,
            examples: [],
          },
          { id: 219928185, definition: 'm?c ph?i', wordType: 16, examples: [] },
          {
            id: 221855695,
            definition: '??a, mang, chuy?n, ?em, ?i l?y',
            wordType: 16,
            examples: [],
          },
          {
            id: 222895700,
            definition: '??n, t?i, ??t ??n',
            wordType: 4,
            examples: [],
          },
          {
            id: 235449927,
            definition: 'nh?n ???c, xin ???c, h?i ???c',
            wordType: 16,
            examples: [],
          },
          {
            id: 235506662,
            definition: 'h?c (thu?c l�ng)',
            wordType: 16,
            examples: [],
          },
          {
            id: 236963731,
            definition: 'sai ai, b?o ai, nh? ai (l�m g�)',
            wordType: 16,
            examples: [],
          },
          {
            id: 239635022,
            definition: 'sinh, ?? (th� v?t; �t khi d�ng cho ng??i)',
            wordType: 16,
            examples: [],
          },
          { id: 240451497, definition: '?n', wordType: 16, examples: [] },
          { id: 251867715, definition: 'b?t ??u', wordType: 4, examples: [] },
          {
            id: 256974021,
            definition: '???c; t�nh',
            wordType: 0,
            examples: [],
          },
          { id: 257514199, definition: 'b?, ch?u', wordType: 16, examples: [] },
          {
            id: 272922754,
            definition: 't�m h?, mua h?, xoay h?, cung c?p',
            wordType: 16,
            examples: [],
          },
          {
            id: 273408495,
            definition: 'l�m cho, khi?n cho',
            wordType: 16,
            examples: [],
          },
          {
            id: 277633437,
            definition: '(t? l�ng) c�t ?i, chu?n',
            wordType: 4,
            examples: [],
          },
          {
            id: 280347210,
            definition: 'tr? n�n, tr? th�nh, th�nh ra, ?i ??n ch?',
            wordType: 4,
            examples: [],
          },
          {
            id: 285770442,
            definition: '???c, c� ???c, ki?m ???c, l?y ???c',
            wordType: 16,
            examples: [],
          },
          { id: 287323008, definition: 'mua', wordType: 16, examples: [] },
          {
            id: 291181410,
            definition: 'to have got c�, ph?i',
            wordType: 16,
            examples: [],
          },
          {
            id: 298980080,
            definition:
              'd?n (ai) v�o th? b�, d?n (ai) v�o ch�n t??ng; l�m (ai) b?i r?i l�ng t�ng kh�ng bi?t ?n n�i ra sao',
            wordType: 16,
            examples: [],
          },
        ],
      };
    },
    getAgreement() {
      return 1;
    },
    getWordTypeReferences() {
      return {
        0: 'kh�ng',
        1: 'danh t?',
        2: 't�nh t?',
        4: 'n?i ??ng t?',
        8: 'tr?ng t?',
        16: 'ngo?i ??ng t?',
        32: 'm?o t?',
        64: 'gi?i t?',
        128: 't? h?n ??nh',
        256: '??i t?',
        512: 'li�n t?',
        1024: 'th�n t?',
        2048: 'vi?t t?t',
        4096: 'ngh?a b�ng',
        8192: '??nh ngh?a',
        16384: 'tr? t?',
      };
    },
    speak(word) {
      const utterance = new SpeechSynthesisUtterance(word);
      speechSynthesis.speak(utterance);
    },
  };
