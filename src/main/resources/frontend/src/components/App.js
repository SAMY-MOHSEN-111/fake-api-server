import Main from "./Main";
import Header from "./Header";
import {useEffect, useReducer} from "react";
import Loader from "./Loader";
import Error from "./Error";
import StartScreen from "./StartScreen";
import Question from "./Question";
import NextButton from "./NextButton";
import ProgressIndicator from "./ProgressIndicator";
import FinishScreen from "./FinishScreen";
import Footer from "./Footer";
import Timer from "./Timer";


const SEC_PER_QUESTION = 30;
const initialState = {
    questions: [],
    status: "loading" /* loading, error, active, ready, finished */,
    index: 0,
    answer: null,
    points: 0,
    highScore: 0,
    secondsRemaining: null,
};

function reducer(state, action) {
    const {type, payload} = action;
    const {questions, index, points, highScore, secondsRemaining, status} = state;

    switch (type) {
        case "dataReceived":
            return {...state, questions: payload, status: "ready"};
        case "dataFailed":
            return {...state, status: "error"};
        case "start":
            return {...state, status: "active", secondsRemaining: questions.length * SEC_PER_QUESTION};
        case "newAnswer":
            const question = questions.at(index);
            return {
                ...state,
                answer: payload,
                points: payload === question.correctOption ? points + question.points : points
            };
        case "nextQuestion":
            return {...state, answer: null, index: index + 1};
        case "finish":
            return {...state, status: "finished", highScore: points > highScore ? points : highScore};
        case "reset":
            return {...initialState, questions: questions, status: "ready", highScore: highScore};
        case "tick":
            return {
                ...state,
                status: secondsRemaining === 0 ? "finished" : status,
                secondsRemaining: secondsRemaining - 1,
            };
        default:
            throw new Error("Unknown Action");
    }
}

export default function App() {
    const [{
        questions,
        status,
        index,
        answer,
        points,
        highScore,
        secondsRemaining
    }, dispatch] = useReducer(reducer, initialState);
    const numQuestions = questions.length;
    const maxPossiblePoints = questions.reduce((acc, cur) => acc + cur.points, 0);
    const finished = !(index + 1 < numQuestions);

    useEffect(function () {
        async function fetchQuestions() {
            try {
                const res = await fetch("http://localhost:8200/api/v1/questions");
                const data = await res.json();
                dispatch({type: "dataReceived", payload: data.data.questions})
            } catch (error) {
                dispatch({type: "dataFailed"})
                console.error("Error Fetching Questions");
            }
        }

        fetchQuestions().then();
    }, []);

    return (
        <div className="app">
            <Header/>
            <Main>
                {status === "loading" && <Loader/>}
                {status === "error" && <Error/>}
                {status === "ready" &&
                    <StartScreen
                        numQuestions={numQuestions}
                        dispatch={dispatch}
                    />
                }
                {status === "active" &&
                    <>
                        <ProgressIndicator
                            index={index}
                            numQuestions={questions.length}
                            points={points}
                            possiblePoints={maxPossiblePoints}
                            answer={answer}
                        />
                        <Question
                            question={questions.at(index)}
                            dispatch={dispatch}
                            answer={answer}
                        />
                        <Footer>
                            <Timer secondsRemaining={secondsRemaining} dispatch={dispatch}/>
                            {
                                answer != null &&
                                <NextButton
                                    dispatch={() => dispatch(!finished ? {type: "nextQuestion"} : {type: "finish"})}>
                                    {!finished ? "Next" : "Finish"}
                                </NextButton>
                            }
                        </Footer>

                    </>
                }
                {status === "finished" &&
                    <FinishScreen
                        points={points}
                        maxPossiblePoints={maxPossiblePoints}
                        highScore={highScore}
                        dispatch={dispatch}
                    />
                }
            </Main>
        </div>
    );
}