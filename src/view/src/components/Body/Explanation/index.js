import VolumeUp from '@mui/icons-material/VolumeUp';
import Edit from '@mui/icons-material/Edit';
import { Box, IconButton, Paper, Tooltip, Typography } from '@mui/material';
import { makeStyles } from '@mui/styles';
import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import Definition from './Definition';
import speakWord from '@store/actions/unknown/speakWord';
import openWordEditor from '@store/actions/app/openWordEditor';

const useStyles = makeStyles(() => ({
  zeroIndex: {
    zIndex: 0,
  },
  defs: {
    flex: 1,
    WebkitFlex: 1,
    msFlex: 1,
    flexGrow: 1,
    WebkitFlexGrow: 1,
    overflow: 'auto',
  },
  segment: {
    width: '100%',
    padding: '1rem',
    marginTop: '1rem',
    marginBottom: '1rem',
    backgroundColor: '#fefefe',
  },
  edit: {
    fontSize: '1.3rem',
    cursor: 'pointer',
  },
}));

const connector = connect(
  state => ({
    viewingWord: state.app.viewingWord,
  }),
  { speakWord, openWordEditor },
);

function Explanation(props) {
  const classes = useStyles();
  const [wordInfo, setWordInfo] = useState(null);

  useEffect(() => {
    try {
      const word =
        props.viewingWord === -1
          ? null
          : window.bridge.getWord(props.viewingWord);

      setWordInfo(word);
    } catch (e) {
      console.error(e.message);
    }
  }, [props.viewingWord]);

  return (
    <Box
      display={props.viewingWord === -1 ? 'none' : 'flex'}
      className={classes.zeroIndex}
      flexDirection="column"
      alignItems="center"
      width="100%"
      height="100%">
      {wordInfo === null ? null : (
        <>
          <Paper className={classes.segment}>
            <Box
              width="100%"
              display="flex"
              flexDirection="row"
              justifyContent="space-between">
              <Typography variant="h4">
                {wordInfo.content}
                &nbsp;
                <Tooltip title="Edit word">
                  <sup>
                    <Edit
                      className={classes.edit}
                      color="disabled"
                      onClick={() => props.openWordEditor(wordInfo.id)}
                    />
                  </sup>
                </Tooltip>
              </Typography>
              <Box display="flex" flexDirection="row" alignItems="center">
                <IconButton
                  size="small"
                  onClick={() => props.speakWord(wordInfo.content)}>
                  <VolumeUp />
                </IconButton>
                {wordInfo.pronunciation.length === 0 ? null : (
                  <>
                    <Box width="1rem" />

                    <Typography variant="h4">
                      {`/${wordInfo.pronunciation}/`}
                    </Typography>
                  </>
                )}
              </Box>
            </Box>
          </Paper>

          <Box width="100%" display="block" className={classes.defs}>
            <Box width="100%" overflow="auto">
              {wordInfo.definitions.map(def => (
                <Definition key={`v/def/${def.id}`} definition={def} />
              ))}
            </Box>
          </Box>
        </>
      )}
    </Box>
  );
}

export default connector(Explanation);
