import { Box, Button, Typography } from '@mui/material';
import React from 'react';
import { connect } from 'react-redux';
import closeWordEditor from '@store/actions/app/closeWordEditor';
import commitChange from '@store/actions/unknown/commitChange';
import deleteWord from '@store/actions/app/deleteWord';

const connector = connect(
  state => ({
    isExistingWord: state.editor.id !== -1,
  }),
  {
    deleteWord,
    closeWordEditor,
    commitChange,
  },
);

function ConfirmSection(props) {
  return (
    <Box
      display="flex"
      flexDirection="row"
      justifyContent="space-between"
      alignItems="center"
      paddingTop="0.5rem"
      paddingBottom="0.5rem">
      <Typography variant="caption">
        You can re-edit whenever you want
      </Typography>

      <Box display="flex" flexDirection="row" alignItems="center">
        <Button variant="text" size="large" onClick={props.closeWordEditor}>
          cancel
        </Button>

        {props.isExistingWord ? (
          <>
            <Box width="1rem" />

            <Button
              variant="contained"
              color="error"
              size="large"
              onClick={props.deleteWord}>
              delete
            </Button>
          </>
        ) : null}

        <Box width="1rem" />

        <Button
          variant="contained"
          color="success"
          size="large"
          onClick={props.commitChange}>
          confirm and save
        </Button>
      </Box>
    </Box>
  );
}

export default connector(ConfirmSection);
