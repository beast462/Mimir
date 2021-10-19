import ActionTypes from '@store/ActionTypes';

// #region helper
const idSet = new Set();

function generateId(prefix) {
  let result;

  do {
    result = (Math.random() * 1e8 + prefix * 1e8) | 0;
  } while (idSet.has(result));

  idSet.add(result);
  return result;
}

function createExample() {
  return {
    id: generateId(3),
    english: 'Example',
    vietnamese: 'Ví dụ',
  };
}

function createDefinition() {
  return {
    id: generateId(2),
    definition: 'Định nghĩa',
    wordType: 0,
    examples: [createExample()],
    relations: [],
  };
}

function createWord() {
  return {
    content: 'w0rd',
    id: -1,
    pronunciation: 'prəˌnənsēˈāSH(ə)n',
    definitions: [createDefinition()],
  };
}
// #endregion

const initialState = createWord();

function reduce(state = initialState, action) {
  switch (action.type) {
    case ActionTypes.EDITOR_ADD_DEFINITION:
      return {
        ...state,
        definitions: [createDefinition(), ...state.definitions],
      };

    case ActionTypes.EDITOR_DELETE_DEFINITION:
      return {
        ...state,
        definitions: state.definitions.filter(d => d.id !== action.payload),
      };

    case ActionTypes.EDITOR_EDIT_DEFINITION:
      return {
        ...state,
        definitions: state.definitions.map(def =>
          def.id === action.payload.id
            ? {
                ...def,
                definition: action.payload.def,
                wordType: action.payload.types,
              }
            : def,
        ),
      };

    case ActionTypes.EDITOR_ADD_EXAMPLE:
      return {
        ...state,
        definitions: state.definitions.map(def =>
          def.id === action.payload
            ? {
                ...def,
                examples: [...def.examples, createExample()],
              }
            : def,
        ),
      };

    case ActionTypes.EDITOR_DELETE_EXAMPLE:
      return {
        ...state,
        definitions: state.definitions.map(def => ({
          ...def,
          examples: def.examples.filter(ex => ex.id !== action.payload),
        })),
      };

    case ActionTypes.EDITOR_EDIT_EXAMPLE:
      return {
        ...state,
        definitions: state.definitions.map(def => ({
          ...def,
          examples: def.examples.map(ex =>
            ex.id !== action.payload.id ? ex : action.payload,
          ),
        })),
      };

    case ActionTypes.EDITOR_EDIT_FIELD:
      return {
        ...state,
        ...((field, value) => {
          switch (field) {
            case 'content':
              return { content: value };

            case 'pronunciation':
              return { pronunciation: value };

            default:
              return {};
          }
        })(action.payload.field, action.payload.value),
      };

    case ActionTypes.EDITOR_ADD_RELATION:
      return {
        ...state,
        definitions: state.definitions.map(def =>
          def.id === action.payload.defId
            ? {
                ...def,
                relations: def.relations.find(
                  rel => rel.id === action.payload.word.id,
                )
                  ? def.relations
                  : [...def.relations, action.payload.word],
              }
            : def,
        ),
      };

    case ActionTypes.EDITOR_DELETE_RELATION:
      return {
        ...state,
        definitions: state.definitions.map(def =>
          def.id === action.payload.defId
            ? {
                ...def,
                relations: def.relations.filter(
                  rel => rel.id !== action.payload.relId,
                ),
              }
            : def,
        ),
      };

    case ActionTypes.EDITOR_PUT_WORD:
      return { ...action.payload };

    case ActionTypes.EDITOR_RESET:
      return createWord();

    default:
      return state;
  }
}

export { reduce, initialState };
