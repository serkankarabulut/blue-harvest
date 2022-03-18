import {
    AppBar,
    Box,
    Button,
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Toolbar,
    Typography
} from "@mui/material";
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import {GENERATE_MOCK_DATA_URL} from "../Constants";
import axios from "axios";

const pages = {
    "All Customer List": "list",
    "Search Customer": "search",
    "Open New Account": "newAccount",
    "Open New Customer": "newCustomer"
};

const AppBarComponent = () => {

    const [open, setOpen] = useState(false);

    const callGenerateMockData = () =>{
        axios.get(GENERATE_MOCK_DATA_URL)
            .catch(error => {
                console.log(error);
            })
    }

    let navigate = useNavigate();

    return (<AppBar position="static">
            <Container maxWidth="xl">
                <Toolbar disableGutters>
                    <Button
                        sx={{my: 2, display: {xs: "none", md: "flex"}}}
                        onClick={() => {
                            navigate("/");
                        }}
                    >
                        <Typography variant="h6" color="White" noWrap component="div" sx={{mr: 2, display: {xs: "none", md: "flex"}}}>
                            Blue Harvest
                        </Typography>
                    </Button>
                    <Box sx={{flexGrow: 1, display: {xs: "none", md: "flex"}}}>
                        {Object.keys(pages).map((page) => {
                            return (<Button
                                    key={page}
                                    sx={{my: 2, color: "white", display: "block"}}
                                    onClick={() => {
                                        navigate("/" + pages[page]);
                                    }}
                                >
                                    {page}
                                </Button>)
                        })}
                        <Button
                            key="mock"
                            sx={{my: 2, color: "white", display: "block"}}
                            onClick={() => {
                                setOpen(true);
                                callGenerateMockData();
                                navigate("/");
                            }}
                        >
                            Populate Mock Data
                        </Button>
                        <Dialog
                            open={open}
                            onClose={()=>setOpen(false)}
                            auto
                            aria-labelledby="alert-dialog-title"
                            aria-describedby="alert-dialog-description"
                        >
                            <DialogTitle id="alert-dialog-title">
                                {"Mock Data"}
                            </DialogTitle>
                            <DialogContent>
                                <DialogContentText id="alert-dialog-description">
                                    Mock Data Created
                                </DialogContentText>
                            </DialogContent>
                            <DialogActions>
                                <Button onClick={()=>setOpen(false)}>Okay</Button>
                            </DialogActions>
                        </Dialog>
                    </Box>
                </Toolbar>
            </Container>
        </AppBar>)
}

export default AppBarComponent;